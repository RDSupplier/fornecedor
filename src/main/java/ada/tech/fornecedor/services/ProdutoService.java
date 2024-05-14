package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Categoria;
import ada.tech.fornecedor.domain.entities.Fabricante;
import ada.tech.fornecedor.domain.entities.Produto;
import ada.tech.fornecedor.domain.mappers.CategoriaMapper;
import ada.tech.fornecedor.domain.mappers.FabricanteMapper;
import ada.tech.fornecedor.domain.mappers.ProdutoMapper;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService{

    private final IProdutoRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public ProdutoDto criarProduto(ProdutoDto produtoDto) {
        List<Categoria> categorias = CategoriaMapper.toEntityList(produtoDto.getCategorias());
        Fabricante fabricante = FabricanteMapper.toEntity(produtoDto.getFabricante());

        categorias.forEach(categoria -> {
            categoria.setProdutos(new ArrayList<>());
            entityManager.merge(categoria);
        });

        entityManager.merge(fabricante);

        Produto produto = ProdutoMapper.toEntity(produtoDto);
        produto.setCategorias(categorias);
        produto.setFabricante(fabricante);

        produto = repository.save(produto);

        Produto finalProduto = produto;
        categorias.forEach(categoria -> {
            categoria.getProdutos().add(finalProduto);
            entityManager.merge(categoria);
        });

        return ProdutoMapper.toDto(produto);
    }

    public List<ProdutoDto> listarProdutos() {
        return repository.findAll().stream().map(ProdutoMapper::toDto).toList();
    }

    public ProdutoDto listarProduto(int id) throws NotFoundException {
        return ProdutoMapper.toDto(searchProdutoById(id));
    }

    public ProdutoDto atualizarProduto(int id, ProdutoDto produtoDto) throws NotFoundException {
        final Produto produto = repository.findById(id).orElseThrow(() -> new NotFoundException(Produto.class, String.valueOf(id)));
        produto.setNomeComercial(produtoDto.getNomeComercial());
        produto.setPrincipioAtivo(produtoDto.getPrincipioAtivo());
        produto.setApresentacao(produtoDto.getApresentacao());
        produto.setLote(produtoDto.getLote());
        produto.setDataFabricacao(produtoDto.getDataFabricacao());
        produto.setCodigoBarras(produtoDto.getCodigoBarras());
        produto.setImagem(produtoDto.getImagem());
        produto.setPreco(produtoDto.getPreco());
        produto.setCargaPerigosa(produtoDto.isCargaPerigosa());
        produto.setVolume(produtoDto.getVolume());
        return ProdutoMapper.toDto(repository.save(produto));
    }

    public void deletarProduto(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Produto searchProdutoById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Produto.class, String.valueOf(id)));
    }
}
