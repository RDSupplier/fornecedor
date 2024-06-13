package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;

import java.util.List;

public interface ICategoriaService {
    CategoriaDto criarCategoria(CategoriaDto categoriaDto);

    List<CategoriaDto> listarCategorias() throws NotFoundException;

    CategoriaDto listarCategoria(int id) throws NotFoundException;

    CategoriaDto atualizarCategoria(int id, CategoriaDto categoriaDto) throws NotFoundException;

    void deletarCategoria(int id) throws NotFoundException;
}
