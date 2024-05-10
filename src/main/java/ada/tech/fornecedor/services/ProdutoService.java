package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Produto;
import ada.tech.fornecedor.domain.mappers.ProdutoMapper;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService{

    private final IProdutoRepository repository;

    public ProdutoDto criarProduto(ProdutoDto produtoDto) {
        Produto produto = ProdutoMapper.toEntity(produtoDto);
        return ProdutoMapper.toDto(repository.save(produto));
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
        produto.setFabricante(produtoDto.getFabricante());
        produto.setFornecedor(produtoDto.getFornecedor());
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
