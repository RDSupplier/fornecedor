package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByNome(String nome);
}
