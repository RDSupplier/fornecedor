package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFabricanteRepository extends JpaRepository<Fabricante, Integer> {
    Fabricante findByCnpj(long cnpj) throws NotFoundException;

    void deleteByCnpj(long cnpj);
}
