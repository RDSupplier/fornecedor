package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.AdminDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;

public interface IAdminService {
    LoginResponse loginAdmin(AdminDto adminDto);
}
