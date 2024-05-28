package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Integer> {
}
