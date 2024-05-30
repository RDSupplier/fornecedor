package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.*;
import ada.tech.fornecedor.repositories.IEstoqueRepository;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueService implements IEstoqueService {

    private final IEstoqueRepository repository;
    private final IFornecedorRepository fornecedorRepository;
    private final IProdutoRepository produtoRepository;

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
    public EstoqueDto adicionarProduto(int estoqueId, int produtoId) throws NotFoundException {
        Estoque estoque = searchEstoqueById(estoqueId);

        if (produtoId <= 0) {
            throw new IllegalArgumentException("O id do produto não pode ser <= 0>");
        }

        Optional<Produto> produtoExistente = produtoRepository.findById(produtoId);
        if (!produtoExistente.isPresent()) {
            throw new NotFoundException(Produto.class, "ID: " + produtoId);
        }

        Produto produto = produtoExistente.get();

        if(estoque.getProdutos().contains(produto)) {
            throw new IllegalArgumentException("O produto já está associado a este estoque");
        }

        estoque.getProdutos().add(produto);
        estoque = repository.save(estoque);

        produto.getEstoques().add(estoque);
        produtoRepository.save(produto);

        return EstoqueMapper.toDto(estoque);
    }

    @Override
    @Transactional
    public EstoqueDto adicionarFornecedor(int estoqueId, int fornecedorId) throws NotFoundException {
        Estoque estoque = searchEstoqueById(estoqueId);

        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findById(fornecedorId);

        Fornecedor fornecedor;
        if (!fornecedorExistente.isPresent()) {
            throw new NotFoundException(Fornecedor.class, "ID: " + fornecedorId);
        }

        fornecedor = fornecedorExistente.get();

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
