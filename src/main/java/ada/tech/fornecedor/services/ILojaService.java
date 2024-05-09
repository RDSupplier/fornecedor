package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;

import java.util.List;

public interface ILojaService {
    LojaDto criarLoja(LojaDto pedido);
    List<LojaDto> listarLojas();
    LojaDto listarLoja(int id) throws NotFoundException;

    LojaDto atualizarLoja(int id, LojaDto pedido) throws NotFoundException;

    void deletarLoja(int id) throws NotFoundException;


}
