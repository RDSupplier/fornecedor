package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.LoginDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Fornecedor;

import java.util.List;

public interface IFornecedorService {
    FornecedorDto criarFornecedor(FornecedorDto fornecedor);
    List<FornecedorDto> listarFornecedores();
    FornecedorDto listarFornecedor(int id) throws NotFoundException;
    FornecedorDto listarFornecedor(String email) throws NotFoundException;
    List<PedidoDto> listarPedidos(int id) throws NotFoundException;
    FornecedorDto atualizarFornecedor(int id, FornecedorDto fornecedor) throws NotFoundException;
    void deletarFornecedor(int id) throws NotFoundException, Exception;
    LoginResponse loginFornecedor(LoginDto loginDto);

    Fornecedor obterFornecedorEntidade(int id);

    String recuperarSenha(String email) throws NotFoundException;

    boolean isOtpCorrect(String email, String otp) throws NotFoundException;

    FornecedorDto redefinirSenha(String email, String senha) throws NotFoundException;
}
