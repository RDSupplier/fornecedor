package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.LoginDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;

import java.util.List;

public interface IFornecedorService {
    FornecedorDto criarFornecedor(FornecedorDto fornecedor);
    List<FornecedorDto> listarFornecedores();
    FornecedorDto listarFornecedor(int id) throws NotFoundException;
    List<PedidoDto> listarPedidos(int id) throws NotFoundException;
    FornecedorDto atualizarFornecedor(int id, FornecedorDto fornecedor) throws NotFoundException;
    void deletarFornecedor(int id) throws NotFoundException;
    LoginResponse loginFornecedor(LoginDto loginDto);
}
