package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IFabricanteService {
    FabricanteDto criarFabricante(FabricanteDto dto);

    List<FabricanteDto> listarFabricantes() throws NotFoundException;

    FabricanteDto listarFabricante(int id) throws NotFoundException;

    FabricanteDto atualizarFabricante(int id, FabricanteDto fabricante) throws NotFoundException;

    void deletarFabricante(int id) throws NotFoundException;
}
