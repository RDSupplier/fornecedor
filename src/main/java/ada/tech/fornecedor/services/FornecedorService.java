package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.*;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.EnderecoMapper;
import ada.tech.fornecedor.domain.mappers.FornecedorMapper;
import ada.tech.fornecedor.domain.mappers.PedidoMapper;
import ada.tech.fornecedor.domain.mappers.ProdutoMapper;
import ada.tech.fornecedor.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FornecedorService implements IFornecedorService {

    private final IFornecedorRepository repository;
    private final IEstoqueRepository estoqueRepository;
    private final IEnderecoRepository enderecoRepository;
    private final IPedidoRepository pedidoRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProdutoService produtoService;
    private final PedidoService pedidoService;


    private Role criarRoleSeNaoExistir(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Override
    @Transactional
    public FornecedorDto criarFornecedor(FornecedorDto fornecedorDto) {
        Endereco endereco = verificarEndereco(fornecedorDto.getEndereco());
        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);
        fornecedor.setEnderecos(endereco);
        Role roleFornecedor = criarRoleSeNaoExistir("ROLE_FORNECEDOR");
        String senhaEncoded = passwordEncoder.encode(fornecedorDto.getSenha());
        fornecedor.setRole(roleFornecedor);
        fornecedor.setSenha(senhaEncoded);
        fornecedor = repository.save(fornecedor);
        return FornecedorMapper.toDto(fornecedor);
    }

    @Override
    public List<FornecedorDto> listarFornecedores() {
        return repository.findAll().stream().map(FornecedorMapper::toDto).toList();
    }

    @Override
    public FornecedorDto listarFornecedor(int id) throws NotFoundException {
        return FornecedorMapper.toDto(searchFornecedorById(id));
    }

    @Override
    public List<PedidoDto> listarPedidos(int id) throws NotFoundException {
        Fornecedor fornecedor = searchFornecedorById(id);

        if (fornecedor == null) {
            throw new NotFoundException(Fornecedor.class, String.valueOf(id));
        }

        Estoque estoque = fornecedor.getEstoques().get(0);

        Map<Integer, PedidoDto> pedidosPorId = new HashMap<>();

        for (Produto produto : estoque.getProdutos()) {
            for (PedidoProduto pedidoProduto : produto.getPedidoProduto()) {
                Pedido pedido = pedidoProduto.getPedidos();

                if (pedido.getFornecedor().getId() == fornecedor.getId()) {
                    PedidoDto pedidoDto = pedidosPorId.getOrDefault(pedido.getId(), PedidoMapper.toDto(pedido));

                    PedidoProdutoDto pedidoProdutoDto = new PedidoProdutoDto();
                    pedidoProdutoDto.setProduto(ProdutoMapper.toDto(produto));
                    pedidoProdutoDto.setQuantidade(pedidoProduto.getQuantidade());

                    boolean produtoJaAdicionado = pedidoDto.getProdutos().stream().anyMatch(p ->
                            p.getProduto().getId() == produto.getId() &&
                                    p.getId() == pedidoProduto.getId());

                    if (!produtoJaAdicionado) {
                        pedidoDto.getProdutos().add(pedidoProdutoDto);
                    }

                    pedidosPorId.put(pedido.getId(), pedidoDto);
                }
            }
        }

        return new ArrayList<>(pedidosPorId.values());
    }

    @Override
    @Transactional
    public FornecedorDto atualizarFornecedor(int id, FornecedorDto fornecedorDto) throws NotFoundException {
        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);
        String senhaEncoded = passwordEncoder.encode(fornecedor.getSenha());

        Optional<Endereco> enderecoExistente = enderecoRepository.findById(fornecedorDto.getEndereco().getId());

        Endereco endereco;
        if(enderecoExistente.isPresent()) {
            EnderecoDto enderecoDto = fornecedorDto.getEndereco();
            endereco = enderecoExistente.get();
            endereco.setRua(enderecoDto.getRua());
            endereco.setNumero(enderecoDto.getNumero());
            endereco.setComplemento(enderecoDto.getComplemento());
            endereco.setBairro(enderecoDto.getBairro());
            endereco.setCidade(enderecoDto.getCidade());
            endereco.setEstado(enderecoDto.getEstado());
            endereco.setCep(enderecoDto.getCep());
        } else {
            throw new NotFoundException(Endereco.class, String.valueOf(enderecoExistente.get().getId()));
        }

        Fornecedor fornecedorExistente = repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));

        fornecedorExistente.setCnpj(fornecedor.getCnpj());
        fornecedorExistente.setNire(fornecedor.getNire());
        fornecedorExistente.setNome(fornecedor.getNome());
        fornecedorExistente.setSenha(senhaEncoded);

        fornecedorExistente.setEnderecos(endereco);

        fornecedorExistente = repository.save(fornecedorExistente);

        return FornecedorMapper.toDto(fornecedorExistente);
    }

    @Override
    @Transactional
    public void deletarFornecedor(int id) throws NotFoundException {
        Fornecedor fornecedor = searchFornecedorById(id);
        List<Estoque> estoques = fornecedor.getEstoques();

        for (Estoque estoque : estoques) {
            for (Produto produto : estoque.getProdutos()) {
                for(PedidoProduto pedidoProduto : produto.getPedidoProduto()) {
                    Pedido pedido = pedidoProduto.getPedidos();
                    pedido.setFornecedor(null);
                    pedidoRepository.save(pedido);
                }
                removerPedidosAssociadosAoProduto(produto);
                produtoService.deletarProduto(produto.getId());
            }

            estoqueRepository.deleteById(estoque.getId());
        }

        repository.deleteById(id);
    }

    private Fornecedor searchFornecedorById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));
    }

    private Endereco verificarEndereco(EnderecoDto enderecoDto) {
        Endereco endereco = EnderecoMapper.toEntity(enderecoDto);

        Optional<Endereco> enderecoExistente = enderecoRepository.findByEnderecoAttributes(endereco);

        if (enderecoExistente.isPresent()) {
            return enderecoExistente.get();
        } else {
            return enderecoRepository.save(endereco);
        }
    }

    public LoginResponse loginFornecedor(LoginDto loginDto) {
        long cnpj = loginDto.getCnpj();
        String senha = loginDto.getSenha();
        Fornecedor fornecedor = repository.findByCnpj(cnpj);
        if (fornecedor != null) {
            String senhaEncoded = fornecedor.getSenha();
            if (passwordEncoder.matches(senha, senhaEncoded)) {
                return new LoginResponse("Login bem-sucedido", true, fornecedor.getId());
            } else {
                return new LoginResponse("Dados incorretos", false, fornecedor.getId());
            }
        } else {
            return new LoginResponse("Fornecedor não encontrado", false, fornecedor.getId());
        }
    }

    private boolean isPedidoAAssociatedWithFornecedor(Pedido pedido, Fornecedor fornecedor) {
        for(PedidoProduto pedidoProduto : pedido.getPedidoProduto()) {
            Produto produto = pedidoProduto.getProdutos();
            for(Estoque estoque : produto.getEstoques()) {
                if(estoque.getFornecedor().getId() == fornecedor.getId()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Fornecedor obterFornecedorEntidade(int id) {
        return repository.findById(id).orElse(null);
    }

    private void removerPedidosAssociadosAoProduto(Produto produto) {
        for (PedidoProduto pedidoProduto : produto.getPedidoProduto()) {
            Pedido pedido = pedidoProduto.getPedidos();
            pedido.getPedidoProduto().remove(pedidoProduto);
        }
    }

    private void removerFornecedorDoPedido(Pedido pedido) {
        pedido.setFornecedor(null);
        pedidoRepository.save(pedido);
    }
}
