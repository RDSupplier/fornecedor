package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.*;
import ada.tech.fornecedor.repositories.IEstoqueRepository;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueService implements IEstoqueService {

    private final IEstoqueRepository repository;
    private final IFornecedorRepository fornecedorRepository;
    private final IProdutoRepository produtoRepository;
    private final EntityManager entityManager;

    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    @Override
    @Transactional
    public EstoqueDto criarEstoque(EstoqueDto estoqueDto) {
        Estoque estoque = EstoqueMapper.toEntity(estoqueDto);

        estoque = repository.save(estoque);

        return EstoqueMapper.toDto(estoque);
    }


    @Override
    public List<EstoqueDto> listarEstoques() {
        return repository.findAll().stream().map(EstoqueMapper::toDto).toList();
    }

    @Override
    public EstoqueDto listarEstoque(int id) throws NotFoundException {
        return EstoqueMapper.toDto(searchEstoqueById(id));
    }

    @Override
    public EstoqueDto atualizarEstoque(int id, EstoqueDto estoqueDto) throws NotFoundException {
        final Estoque estoque = repository.findById(id).orElseThrow(() -> new NotFoundException(Estoque.class, String.valueOf(id)));
        return EstoqueMapper.toDto(repository.save(estoque));
    }

    @Override
    public void deletarEstoque(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public EstoqueDto adicionarProduto(int estoqueId, ProdutoEstoque produtoEstoqueRequest) throws NotFoundException {
        Estoque estoque = searchEstoqueById(estoqueId);

        if (produtoEstoqueRequest == null) {
            throw new IllegalArgumentException("O objeto produtoEstoqueRequest não pode ser nulo");
        }

        Produto produto = produtoEstoqueRequest.getProdutos();
        if (produto == null) {
            throw new IllegalArgumentException("O objeto Produto dentro de produtoEstoqueRequest não pode ser nulo");
        }

        Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());
        if (!produtoExistente.isPresent()) {
            throw new NotFoundException(Produto.class, "ID: " + produto.getId());
        }

        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setProdutos(produto);
        produtoEstoque.setEstoques(estoque);
        produtoEstoque.setQuantidade(produtoEstoqueRequest.getQuantidade());

        estoque.getProdutoEstoques().add(produtoEstoque);
        estoque = repository.save(estoque);

        return EstoqueMapper.toDto(estoque);
    }

    @Override
    @Transactional
    public EstoqueDto adicionarFornecedor(int estoqueId, FornecedorDto fornecedorDto) throws NotFoundException {
        Estoque estoque = searchEstoqueById(estoqueId);

        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(fornecedorDto.getId());

        Fornecedor fornecedor;
        if (fornecedorExistente.isPresent()) {
            fornecedor = fornecedorExistente.get();
        } else {
            fornecedor = FornecedorMapper.toEntity(fornecedorService.criarFornecedor(fornecedorDto));
        }

        if (estoque.getFornecedor() == null) {
            estoque.setFornecedor(fornecedor);
        } else {
            throw new IllegalStateException("Este estoque já está associado a um fornecedor.");
        }

        repository.save(estoque);

        return EstoqueMapper.toDto(estoque);
    }

    private Estoque searchEstoqueById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Estoque.class, String.valueOf(id)));
    }
}
