package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFabricanteRepository extends JpaRepository<Fabricante, Integer> {
    Fabricante findByCnpj(long cnpj);
}
