package ada.tech.fornecedor.services;
import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.PedidoProdutoDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.PedidoMapper;
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


            int quantidadeDisponivel = Math.min(pedidoProdutoDto.getQuantidade(), estoqueProduto.getQuantidade());

            volumeTotalProduto = produto.getVolume() * pedidoProdutoDto.getQuantidade();
            double precoTotalProduto = produto.getPreco() * pedidoProdutoDto.getQuantidade();
            totalPedido += precoTotalProduto;

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setPedidos(pedido);
            pedidoProduto.setProdutos(produto);
            pedidoProduto.setQuantidade(quantidadeDisponivel);
            pedidoProduto.setVolumeTotal(volumeTotalProduto);

            pedidoProdutos.add(pedidoProduto);

            estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() - pedidoProdutoDto.getQuantidade());
            estoqueProdutoRepository.save(estoqueProduto);
        }

        pedido.setTotal(totalPedido);
        pedido.setVolumeTotal(volumeTotalProduto);
        pedido.setStatus(pedidoDto.getStatus());

        Pedido pedidoSalvo = repository.save(pedido);

        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            pedidoProduto.setPedidos(pedidoSalvo);
        }

        pedidoSalvo.getPedidoProduto().addAll(pedidoProdutos);
        pedidoSalvo = repository.save(pedidoSalvo);

        logisticaService.enviarDadosLogistica(pedidoSalvo);

        return PedidoMapper.toDto(pedidoSalvo);
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
        final Pedido pedido = repository.findById(id).orElseThrow(() -> new NotFoundException(Pedido.class, String.valueOf(id)));
        pedido.setData(LocalDate.now());
        pedido.setHorario(LocalTime.now());
        pedido.setTotal(pedidoDto.getTotal());
        pedido.setStatus(pedidoDto.getStatus());

        // caso o status do pedido for cancelado ou recusado, retornar os produtos ao estoque
        if (pedido.getStatus().equalsIgnoreCase("cancelado") || pedido.getStatus().equalsIgnoreCase("recusado")) {
            System.out.println("CANCELADO OU RECUSADO");

            for (PedidoProdutoDto pedidoProdutoDto : pedidoDto.getProdutos()) {
                System.out.println(pedidoDto.getProdutos()); // lista de produtos
                System.out.println(pedidoProdutoDto); // cada produto, que está no formato pedidoProdutoDto

                System.out.println("Retornando produto de id: " + pedidoProdutoDto.getId() + " na quantidade: " + pedidoProdutoDto.getQuantidade() + " ao estoque");
                System.out.println("Id do estoque do fornecedor: " + pedidoDto.getFornecedor());

                // obter o produto
                Produto produto = produtoRepository.findById(pedidoProdutoDto.getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                // obter o estoque do fornecedor
                Fornecedor fornecedor = fornecedorRepository.findById(pedidoDto.getFornecedor())
                        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
                Estoque estoque = fornecedor.getEstoques().get(0); // capturando o estoque

                // obter o estoqueProduto
                EstoqueProduto estoqueProduto = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

                // atualizar a quantidade no estoque
                estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() + pedidoProdutoDto.getQuantidade());
                estoqueProdutoRepository.save(estoqueProduto);

                // verificação
                System.out.println("Produto ID: " + pedidoProdutoDto.getId() + " atualizado no estoque com nova quantidade: " + estoqueProduto.getQuantidade());
            }
        }

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
