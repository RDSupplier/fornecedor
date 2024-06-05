package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.AdminDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.entities.Admin;

public interface IAdminService {
    LoginResponse loginAdmin(AdminDto adminDto);
    Admin obterAdminEntidade(int id);
}
