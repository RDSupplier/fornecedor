package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.AdminDto;
import ada.tech.fornecedor.domain.dto.LoginDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.entities.Admin;
import ada.tech.fornecedor.services.IAdminService;
import ada.tech.fornecedor.services.ILojaService;
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
    private final ILojaService lojaService;
    private final IAdminService adminService;
    @Autowired
    public AuthController(ILojaService lojaService, IAdminService adminService){
        this.lojaService = lojaService;
        this.adminService = adminService;
    }
    @PostMapping(path = "/login/loja")
    public ResponseEntity<?> loginLoja(@RequestBody LoginDto loginDTO) {
        LoginResponse loginResponse = lojaService.loginLoja(loginDTO);
        if (loginResponse.getStatus()) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @PostMapping(path = "/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminDto adminDto) {
        LoginResponse loginResponse = adminService.loginAdmin(adminDto);
        if (loginResponse.getStatus()) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

}
