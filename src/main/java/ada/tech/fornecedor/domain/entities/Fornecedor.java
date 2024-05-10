package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_fornecedor")

public class Fornecedor {
    private long cnpj;
    private String nome;
    private String senha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
