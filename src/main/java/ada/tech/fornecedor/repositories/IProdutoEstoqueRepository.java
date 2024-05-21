package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque, Integer> {
}
