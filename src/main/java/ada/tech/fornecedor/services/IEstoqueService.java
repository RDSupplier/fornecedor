package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface IEstoqueService {
    EstoqueDto criarEstoque(EstoqueDto estoqueDto);

    List<EstoqueDto> listarEstoques();

    EstoqueDto listarEstoque(int id) throws NotFoundException;

    EstoqueDto atualizarEstoque(int id, EstoqueDto estoqueDto) throws NotFoundException;

    void deletarEstoque(int id) throws NotFoundException;
}
