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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco enderecos;

    @Column(length = 14, unique = true)
    private long cnpj;

    @Column(length = 11, unique = true)
    private long nire;

    @Column(length = 50)
    private String nome;

    private String senha;
}
