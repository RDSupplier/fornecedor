package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.AdminDto;
import ada.tech.fornecedor.domain.dto.LoginDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.entities.Admin;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.security.TokenResponseDto;
import ada.tech.fornecedor.security.TokenService;
import ada.tech.fornecedor.services.IAdminService;
import ada.tech.fornecedor.services.IFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fornecedor")
public class AuthController {

    private final IAdminService adminService;
    private final IFornecedorService fornecedorService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    public AuthController( IAdminService adminService, IFornecedorService fornecedorService){
        this.adminService = adminService;
        this.fornecedorService = fornecedorService;
    }


    @PostMapping(path = "/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDto adminDto) {
        LoginResponse loginResponse = adminService.loginAdmin(adminDto);
        if (loginResponse.getStatus()) {
            Admin admin = adminService.obterAdminEntidade(loginResponse.getId());
            var token = tokenService.generateTokenAdmin(admin);
            TokenResponseDto tokenResponse = new TokenResponseDto(token);
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @PostMapping(path = "/login/fornecedor")
    public ResponseEntity<?> loginLoja(@RequestBody LoginDto loginDTO) {
        LoginResponse loginResponse = fornecedorService.loginFornecedor(loginDTO);
        if (loginResponse.getStatus()) {
            Fornecedor fornecedor = fornecedorService.obterFornecedorEntidade(loginResponse.getId());
            var token = tokenService.generateTokenFornecedor(fornecedor);
            TokenResponseDto tokenResponse = new TokenResponseDto(token);
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }


}
