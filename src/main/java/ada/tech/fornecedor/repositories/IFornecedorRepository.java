package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Admin;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Fornecedor findByCnpj(long cnpj);
    Optional<Fornecedor> findByEmail(String email);
}
