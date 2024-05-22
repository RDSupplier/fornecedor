package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
