package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;

import java.util.List;

public interface IProdutoService {
    ProdutoDto criarProduto(ProdutoDto produtoDto) throws NotFoundException;
    List<ProdutoDto> listarProdutos();
    ProdutoDto listarProduto(int id) throws NotFoundException;
    ProdutoDto atualizarProduto(int id, ProdutoDto produtoDto) throws NotFoundException;
    void deletarProduto(int id) throws NotFoundException;
}
