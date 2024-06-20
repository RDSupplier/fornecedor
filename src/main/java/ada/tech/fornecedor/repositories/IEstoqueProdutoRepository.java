package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.EstoqueProduto;
import ada.tech.fornecedor.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Integer> {
    public boolean existsByEstoqueAndProduto(Estoque estoque, Produto produto);
    public EstoqueProduto findByEstoqueAndProduto(Estoque estoque, Produto produto);
}
