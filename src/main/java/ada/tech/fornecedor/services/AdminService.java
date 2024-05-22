package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.AdminDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.entities.Admin;
import ada.tech.fornecedor.domain.entities.Role;
import ada.tech.fornecedor.repositories.IAdminRepository;
import ada.tech.fornecedor.repositories.IRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService{

    private final IAdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;


    public AdminService(IAdminRepository adminRepository, PasswordEncoder passwordEncoder, IRoleRepository roleRepository){
        this.repository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    private Admin criarAdminSeNaoExistir() {
        Role roleAdmin = criarRoleSeNaoExistir("ROLE_ADMIN");
        Admin admin = repository.findByNome("admin");
        String senhaEncoded = passwordEncoder.encode("admin");
        if (admin == null) {
            admin = new Admin();
            admin.setNome("admin");
            admin.setSenha(senhaEncoded);
            admin.setRole(roleAdmin);
            admin = repository.save(admin);
        }
        return admin;
    }
    private Role criarRoleSeNaoExistir(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Override
    public LoginResponse loginAdmin(AdminDto adminDto) {

        criarAdminSeNaoExistir();

        String nome = adminDto.getNome();
        String senha = adminDto.getSenha();

        Admin admin = repository.findByNome(nome);

        if (admin != null) {
            String senhaEncoded = admin.getSenha();
            if (passwordEncoder.matches(senha, senhaEncoded)) {
                return new LoginResponse("Login bem-sucedido", true);
            } else {
                return new LoginResponse("Dados incorretos", false);
            }
        } else {
            return new LoginResponse("Admin não encontrado", false);
        }
    }
}