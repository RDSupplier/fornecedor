package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.*;
import ada.tech.fornecedor.repositories.IEstoqueProdutoRepository;
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
    private final IEstoqueProdutoRepository estoqueProdutoRepository;
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
    public EstoqueDto adicionarProduto(int estoqueId, EstoqueProdutoDto estoqueProdutoDto) throws NotFoundException {
        Estoque estoque = searchEstoqueById(estoqueId);

        Produto produto = produtoRepository.findById(estoqueProdutoDto.getProdutoDto().getId())
                .orElseThrow(() -> new NotFoundException(Produto.class, "ID: " + estoqueProdutoDto.getProdutoDto().getId()));

        EstoqueProduto estoqueProduto = estoqueProdutoRepository.findByEstoqueAndProduto(estoque, produto);

        if (estoqueProduto == null) {
            estoqueProduto = EstoqueProdutoMapper.toEntity(estoqueProdutoDto);
            estoqueProduto.setEstoque(estoque);
            estoqueProduto.setProduto(produto);
        } else {
            estoqueProduto.setQuantidade(estoqueProdutoDto.getQuantidade());
        }

        estoqueProduto.setQuantidadeReservada(0);

        estoqueProdutoRepository.save(estoqueProduto);

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
