package ada.tech.fornecedor.repositories;

import ada.tech.fornecedor.domain.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IEnderecoRepository extends JpaRepository<Endereco, Integer> {
    @Query("SELECT e FROM Endereco e WHERE e.rua = :#{#endereco.rua} AND e.numero = :#{#endereco.numero} AND e.bairro = :#{#endereco.bairro} AND e.cidade = :#{#endereco.cidade} AND e.estado = :#{#endereco.estado} AND e.cep = :#{#endereco.cep}")
    Optional<Endereco> findByEnderecoAttributes(Endereco endereco);
}
