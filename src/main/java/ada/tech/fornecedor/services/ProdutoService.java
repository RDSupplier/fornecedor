package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.FabricanteMapper;
import ada.tech.fornecedor.domain.mappers.ProdutoMapper;
import ada.tech.fornecedor.repositories.ICategoriaRepository;
import ada.tech.fornecedor.repositories.IFabricanteRepository;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    private final IFabricanteService fabricanteService;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public ProdutoDto criarProduto(ProdutoDto produtoDto) throws NotFoundException {
        Fabricante fabricante = FabricanteMapper.toEntity(produtoDto.getFabricante());
        Fabricante fabricanteExistente = fabricanteRepository.findByCnpj(fabricante.getCnpj());

        if (fabricanteExistente != null) {
            produtoDto.setFabricante(FabricanteMapper.toDto(fabricanteExistente));
        }

        Produto produto = ProdutoMapper.toEntity(produtoDto);
        produto.setFabricante(fabricanteExistente);
        produto.setStatus("Ativo");

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
        Fabricante fabricante = FabricanteMapper.toEntity(produtoDto.getFabricante());
        Fabricante fabricanteExistente = fabricanteRepository.findByCnpj(fabricante.getCnpj());

        if (fabricanteExistente != null) {
            fabricanteExistente.setNome(fabricante.getNome());
            fabricanteExistente.setCnpj(fabricante.getCnpj());
            fabricanteExistente = fabricanteRepository.save(fabricanteExistente);
        } else {
            fabricanteExistente = fabricanteRepository.save(fabricante);
        }

        produtoDto.setFabricante(FabricanteMapper.toDto(fabricanteExistente));

        final Produto produto = searchProdutoById(id);

        List<String> categoriaNomes = produtoDto.getCategorias().stream()
                .map(CategoriaDto::getCategoria)
                .collect(Collectors.toList());

        List<Categoria> categoriasExistentes = categoriaRepository.findAllByCategoriaIn(categoriaNomes);

        if (categoriasExistentes.size() != categoriaNomes.size()) {
            throw new NotFoundException(Categoria.class, "Algumas categorias não foram encontradas.");
        }

        for (Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produto.getCategorias().clear();

        for (Categoria categoria : categoriasExistentes) {
            produto.getCategorias().add(categoria);
            if (!categoria.getProdutos().contains(produto)) {
                categoria.getProdutos().add(produto);
            }
        }

        produto.setNomeComercial(produtoDto.getNomeComercial());
        produto.setPrincipioAtivo(produtoDto.getPrincipioAtivo());
        produto.setApresentacao(produtoDto.getApresentacao());
        produto.setCodigoBarras(produtoDto.getCodigoBarras());
        produto.setImagem(produtoDto.getImagem());
        produto.setPreco(produtoDto.getPreco());
        produto.setCargaPerigosa(produtoDto.isCargaPerigosa());
        produto.setVolume(produtoDto.getVolume());
        produto.setFabricante(fabricanteExistente);
        produto.setStatus(produtoDto.getStatus());


        return ProdutoMapper.toDto(repository.save(produto));
    }

    @Transactional
    public void deletarProduto(int id) throws NotFoundException {
        Produto produto = entityManager.find(Produto.class, id);
        if (produto != null) {
            removerCategorias(produto);
            removerPedidos(produto);
            entityManager.remove(produto);
        } else {
            throw new NotFoundException(Produto.class, String.valueOf(id));
        }
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

    public void removerCategorias(Produto produto) {
        for (Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produto.getCategorias().clear();
        entityManager.merge(produto);
    }

    public void removerPedidos(Produto produto) {
        for (PedidoProduto pedidoProduto : produto.getPedidoProduto()) {
            Pedido pedido = pedidoProduto.getPedidos();
            pedido.getPedidoProduto().remove(pedidoProduto);
            pedidoProduto.setProdutos(null);
        }
    }

    private Produto searchProdutoById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Produto.class, String.valueOf(id)));
    }
}
