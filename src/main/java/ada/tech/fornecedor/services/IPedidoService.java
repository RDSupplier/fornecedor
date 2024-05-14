package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Pedido;

import java.util.List;

public interface IPedidoService {
    PedidoDto criarPedido(PedidoDto pedido);
    List<PedidoDto> listarPedidos();
    PedidoDto listarPedido(int id) throws NotFoundException;
    PedidoDto atualizarPedido(int id, PedidoDto pedido) throws NotFoundException;
    void deletarPedido(int id) throws NotFoundException;

//    String calcularDistanciaPedido(int id) throws NotFoundException;
}
