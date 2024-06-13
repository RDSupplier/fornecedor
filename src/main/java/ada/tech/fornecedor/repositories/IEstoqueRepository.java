package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstoqueRepository extends JpaRepository<Estoque, Integer> {
}
