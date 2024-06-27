package ada.tech.fornecedor.services;
import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.PedidoProdutoDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.PedidoMapper;
import ada.tech.fornecedor.domain.mappers.PedidoProdutoMapper;
import ada.tech.fornecedor.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    private final IPedidoRepository repository;
    private final IProdutoRepository produtoRepository;
    private final IFornecedorRepository fornecedorRepository;
    private final IEnderecoRepository enderecoRepository;
    private final LogisticaService logisticaService;

    private final IEstoqueRepository estoqueRepository;

    private final IEstoqueProdutoRepository estoqueProdutoRepository;

    @Autowired
    private ApplicationContext applicationContext;

    public Endereco mockEnderecoDestino() {
        return Endereco.builder()
                .rua("Avenida Paulista")
                .numero("138")
                .complemento("bloco b")
                .bairro("itarare")
                .cidade("São Paulo")
                .estado("SP")
                .cep(11350186)
                .build();
    }

    @Override
    public PedidoDto criarPedido(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();
        List<PedidoProduto> pedidoProdutos = new ArrayList<>();
        pedido.setData(LocalDate.now());
        pedido.setHorario(LocalTime.now());
        pedido.setStatus("Pendente");
        double totalPedido = 0.0;
        double volumeTotalProduto = 0.0;

        Fornecedor fornecedor = fornecedorRepository.findById(pedidoDto.getFornecedor())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        pedido.setFornecedor(fornecedor);

        Endereco endereco = fornecedor.getEnderecos();
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado para o fornecedor");
        }

        Endereco enderecoDestino = mockEnderecoDestino();
        fornecedor.setEnderecos(endereco);
        pedido.setEndereco(enderecoDestino);

        boolean todosProdutosZero = pedidoDto.getProdutos().stream().allMatch(p -> p.getQuantidade() == 0);

        if (todosProdutosZero) {
            throw new IllegalArgumentException("Não é possível criar um pedido com todos os produtos tendo quantidade zero");
        }

        for (PedidoProdutoDto pedidoProdutoDto : pedidoDto.getProdutos()) {
            Produto produto = produtoRepository.findById(pedidoProdutoDto.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Estoque estoque = fornecedor.getEstoques().get(0);
            EstoqueProduto estoqueProduto = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

            if ("Inativo".equalsIgnoreCase(produto.getStatus())) {
                throw new IllegalArgumentException("Produto " + produto.getNomeComercial() + " não pode ser adicionado ao pedido por estar inativo");
            }

            if (pedidoProdutoDto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade do produto deve ser maior que zero");
            }

            int quantidadeSolicitada = pedidoProdutoDto.getQuantidade();
            int quantidadeDisponivel = estoqueProduto.reservarQuantidadeParcial(quantidadeSolicitada);
            int quantidadeAtendida = 0;

            if (quantidadeDisponivel < quantidadeSolicitada) {
                quantidadeAtendida = quantidadeDisponivel;
            } else {
                quantidadeAtendida = quantidadeSolicitada;
            }

            volumeTotalProduto += produto.getVolume() * quantidadeDisponivel;
            double precoTotalProduto = produto.getPreco() * quantidadeDisponivel;
            totalPedido += precoTotalProduto;

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setPedidos(pedido);
            pedidoProduto.setProdutos(produto);
            pedidoProduto.setQuantidade(quantidadeSolicitada);
            pedidoProduto.setQuantidadeAtendida(quantidadeDisponivel);
            pedidoProduto.setVolumeTotal(produto.getVolume() * quantidadeSolicitada);

            pedidoProdutos.add(pedidoProduto);
        }

        pedido.setTotal(totalPedido);
        pedido.setVolumeTotal(volumeTotalProduto);
        pedido.setStatus("Pendente");

        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            pedidoProduto.setPedidos(pedido);
        }

        pedido.getPedidoProduto().addAll(pedidoProdutos);
        pedido = repository.save(pedido);

        logisticaService.enviarDadosLogistica(pedido);

        return PedidoMapper.toDto(pedido);
    }

    @Override
    public List<PedidoDto> listarPedidos() {
        return repository.findAll().stream().map(PedidoMapper::toDto).toList();
    }

    @Override
    public PedidoDto listarPedido(int id) throws NotFoundException {
        return PedidoMapper.toDto(searchPedidoById(id));
    }


    @Override
    public PedidoDto atualizarPedido(int id, PedidoDto pedidoDto) throws NotFoundException {
        final Pedido pedido = searchPedidoById(id);
        PedidoDto dto = PedidoMapper.toDto(pedido);
        pedido.setData(LocalDate.now());
        pedido.setHorario(LocalTime.now());
        pedido.setTotal(pedidoDto.getTotal());

        switch (pedido.getStatus().toLowerCase()) {
            case "pendente":
                if (!pedidoDto.getStatus().equalsIgnoreCase("em separação")) {
                    throw new IllegalStateException("Não é possível alterar o status de um pedido pendente para: " + pedidoDto.getStatus());
                }
                break;
            case "em separação":
                if (!pedidoDto.getStatus().equalsIgnoreCase("cancelado") &&
                        !pedidoDto.getStatus().equalsIgnoreCase("em transporte")) {
                    throw new IllegalStateException("Não é possível alterar o status de um pedido em separação para: " + pedidoDto.getStatus());
                }
                break;
            case "em transporte":
                if (!pedidoDto.getStatus().equalsIgnoreCase("cancelado") &&
                        !pedidoDto.getStatus().equalsIgnoreCase("recusado") &&
                        !pedidoDto.getStatus().equalsIgnoreCase("entregue")) {
                    throw new IllegalStateException("Não é possível alterar o status de um pedido em transporte para: " + pedidoDto.getStatus());
                }
                break;
            case "entregue":
            case "cancelado":
            case "recusado":
                throw new IllegalStateException("Não é possível alterar o status de um pedido que já foi entregue, cancelado ou recusado.");
            default:
                throw new IllegalStateException("Status de pedido inválido: " + pedido.getStatus());
        }

        for (PedidoProdutoDto pedidoProdutoDto : dto.getProdutos()) {
            Produto produto = produtoRepository.findById(pedidoProdutoDto.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Fornecedor fornecedor = fornecedorRepository.findById(pedidoDto.getFornecedor())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
            Estoque estoque = fornecedor.getEstoques().get(0);

            EstoqueProduto estoqueProduto = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

            switch (pedido.getStatus().toLowerCase()) {
                case "pendente":
                    if (!pedidoDto.getStatus().equalsIgnoreCase("em separação")) {
                        estoqueProduto.confirmarReserva(pedidoProdutoDto.getQuantidadeAtendida());
                    }
                    break;
                case "em separação":
                    if (pedidoDto.getStatus().equalsIgnoreCase("em transporte")) {
                        estoqueProduto.confirmarReserva(pedidoProdutoDto.getQuantidadeAtendida());
                    } else if (pedidoDto.getStatus().equalsIgnoreCase("cancelado")) {
                        estoqueProduto.cancelarReserva(pedidoProdutoDto.getQuantidadeAtendida(), "recusado");
                    }
                    break;
                case "em transporte":
                    if (pedidoDto.getStatus().equalsIgnoreCase("cancelado") ||
                            pedidoDto.getStatus().equalsIgnoreCase("recusado")) {
                        estoqueProduto.cancelarReserva(pedidoProdutoDto.getQuantidadeAtendida(), "cancelado");
                    }
                    break;
                default:
                    break;
            }
        }

        pedido.setStatus(pedidoDto.getStatus());
        pedido.setVolumeTotal(pedidoDto.getVolumeTotal());
        return PedidoMapper.toDto(repository.save(pedido));
    }



    @Override
    public void deletarPedido(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Pedido searchPedidoById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Pedido.class, String.valueOf(id)));
    }
}
