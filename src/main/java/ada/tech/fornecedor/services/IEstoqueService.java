package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;

import java.util.List;

public interface IEstoqueService {
    EstoqueDto criarEstoque(EstoqueDto estoqueDto);

    List<EstoqueDto> listarEstoques();

    EstoqueDto listarEstoque(int id) throws NotFoundException;

    EstoqueDto atualizarEstoque(int id, EstoqueDto estoqueDto) throws NotFoundException;

    void deletarEstoque(int id) throws NotFoundException;

    EstoqueDto adicionarProduto(int estoqueId, int produtoId) throws NotFoundException;

    EstoqueDto adicionarFornecedor(int estoqueId, int fornecedorId) throws NotFoundException;
}

