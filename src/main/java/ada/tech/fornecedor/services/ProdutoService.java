package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Categoria;
import ada.tech.fornecedor.domain.entities.Fabricante;
import ada.tech.fornecedor.domain.entities.Produto;
import ada.tech.fornecedor.domain.mappers.FabricanteMapper;
import ada.tech.fornecedor.domain.mappers.ProdutoMapper;
import ada.tech.fornecedor.repositories.ICategoriaRepository;
import ada.tech.fornecedor.repositories.IFabricanteRepository;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService{

    private final IProdutoRepository repository;
    private final IFabricanteRepository fabricanteRepository;
    private final ICategoriaRepository categoriaRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public ProdutoDto criarProduto(ProdutoDto produtoDto) throws NotFoundException {
        Fabricante fabricante = FabricanteMapper.toEntity(produtoDto.getFabricante());
        Fabricante fabricanteExistente = fabricanteRepository.findByCnpj(fabricante.getCnpj());

        if (fabricanteExistente != null) {
            produtoDto.setFabricante(FabricanteMapper.toDto(fabricanteExistente));
        } else {
            fabricanteExistente = fabricanteRepository.save(fabricante);
            produtoDto.setFabricante(FabricanteMapper.toDto(fabricanteExistente));
        }

        Produto produto = ProdutoMapper.toEntity(produtoDto);
        produto.setFabricante(fabricanteExistente);

        adicionarCategorias(produto, produtoDto.getCategorias());

        produto = repository.save(produto);

        return ProdutoMapper.toDto(produto);
    }

    public List<ProdutoDto> listarProdutos() {
        return repository.findAll().stream().map(ProdutoMapper::toDto).toList();
    }

    public ProdutoDto listarProduto(int id) throws NotFoundException {
        return ProdutoMapper.toDto(searchProdutoById(id));
    }

    @Transactional
    public ProdutoDto atualizarProduto(int id, ProdutoDto produtoDto) throws NotFoundException {
        final Produto produto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Produto.class, String.valueOf(id)));

        FabricanteDto fabricanteDto = produtoDto.getFabricante();
        Fabricante fabricante = FabricanteMapper.toEntity(fabricanteDto);
        Fabricante fabricanteExistente = fabricanteRepository.findByCnpj(fabricante.getCnpj());

        if (fabricanteExistente != null) {
            produto.setFabricante(fabricanteExistente);
        } else {
            throw new NotFoundException(Fabricante.class, String.valueOf(fabricanteDto));
        }

        List <String> categoriaNomes = produtoDto.getCategorias().stream()
                .map(CategoriaDto::getCategoria)
                .collect(Collectors.toList());

        List <Categoria> categoriasExistentes = categoriaRepository.findAllByCategoriaIn(categoriaNomes);

        if (categoriasExistentes.size() != categoriaNomes.size()) {
            throw new NotFoundException(Categoria.class, "Algumas categorias não foram encontradas.");
        }

        produto.setNomeComercial(produtoDto.getNomeComercial());
        produto.setPrincipioAtivo(produtoDto.getPrincipioAtivo());
        produto.setApresentacao(produtoDto.getApresentacao());
        produto.setCodigoBarras(produtoDto.getCodigoBarras());
        produto.setImagem(produtoDto.getImagem());
        produto.setPreco(produtoDto.getPreco());
        produto.setCargaPerigosa(produtoDto.isCargaPerigosa());
        produto.setVolume(produtoDto.getVolume());
        produto.setCategorias(categoriasExistentes);

        return ProdutoMapper.toDto(repository.save(produto));
    }

    public void deletarProduto(int id) throws NotFoundException {
        Produto produto = searchProdutoById(id);

        removeProdutoFromCategorias(produto);

        repository.deleteById(id);
    }

    private void adicionarCategorias(Produto produto, List<CategoriaDto> categoriaDtos) throws NotFoundException {
        List<String> categoriaNomes = categoriaDtos.stream()
                .map(CategoriaDto::getCategoria)
                .collect(Collectors.toList());

        List<Categoria> categoriasExistentes = categoriaRepository.findAllByCategoriaIn(categoriaNomes);

        if (categoriasExistentes.size() != categoriaNomes.size()) {
            throw new NotFoundException(Categoria.class, "Algumas categorias não foram encontradas.");
        }

        for(Categoria categoria : categoriasExistentes) {
            categoria.getProdutos().add(produto);
        }

        produto.setCategorias(categoriasExistentes);
    }

    public void removeProdutoFromCategorias(Produto produto) {
        for (Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
    }

    private Produto searchProdutoById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Produto.class, String.valueOf(id)));
    }
}
