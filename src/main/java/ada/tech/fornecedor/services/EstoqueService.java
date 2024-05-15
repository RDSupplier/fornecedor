package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.*;
import ada.tech.fornecedor.repositories.IEstoqueRepository;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService implements IEstoqueService {

    private final IEstoqueRepository repository;
    private final IFornecedorRepository fornecedorRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public EstoqueDto criarEstoque(EstoqueDto estoqueDto) {
        Estoque estoque = EstoqueMapper.toEntity(estoqueDto);
        Fornecedor fornecedor = FornecedorMapper.toEntity(estoqueDto.getFornecedor());
        fornecedor = fornecedorRepository.save(fornecedor);

        List<Produto> produtos = ProdutoMapper.toEntityList(estoqueDto.getProdutos());

        for (Produto produto : produtos) {
            Fabricante fabricante = entityManager.merge(produto.getFabricante());
            produto.setFabricante(fabricante);

            List<Categoria> categorias = new ArrayList<>();
            for (Categoria categoria : produto.getCategorias()) {
                Categoria categoriaPersistida = entityManager.find(Categoria.class, categoria.getId());
                if (categoriaPersistida == null) {
                    categoriaPersistida = entityManager.merge(categoria);
                    categoriaPersistida.setProdutos(new ArrayList<>());
                }
                categoriaPersistida.getProdutos().add(produto);
            }

            produto.setCategorias(categorias);

            if (produto.getEstoques() == null) {
                produto.setEstoques(new ArrayList<>());
            }

            produto.getEstoques().add(estoque);

            entityManager.persist(produto);
        }

        estoque.setProdutos(produtos);
        estoque.setFornecedor(fornecedor);
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
        estoque.setQuantidade(estoqueDto.getQuantidade());
        return EstoqueMapper.toDto(repository.save(estoque));
    }

    @Override
    public void deletarEstoque(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Estoque searchEstoqueById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Estoque.class, String.valueOf(id)));
    }
}
