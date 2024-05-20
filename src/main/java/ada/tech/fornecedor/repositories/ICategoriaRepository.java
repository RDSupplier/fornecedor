package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAllByCategoriaIn(List<String> categorias);
}
