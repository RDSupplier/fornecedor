package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Loja;
import ada.tech.fornecedor.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Integer> {
}
