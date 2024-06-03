package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco")
    private Endereco enderecos;

    @OneToMany(mappedBy = "fornecedor")
    private List<Estoque> estoques = new ArrayList<>();

    @Size(max = 14, message = "O CNPJ do fornecedor não pode exceder 14 caracteres")
    @Column(length = 14, unique = true)
    private long cnpj;

    @Size(max = 11, message = "O NIRE do fornecedor não pode exceder 11 caracteres")
    @Column(length = 11, unique = true)
    private long nire;

    @Column(length = 50)
    private String nome;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
