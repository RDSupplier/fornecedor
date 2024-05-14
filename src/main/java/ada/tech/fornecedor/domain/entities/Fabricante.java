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
@Table(name="tb_fabricante")

public class Fabricante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    @JoinTable(
            name = "fabricante_produto",
            joinColumns = @JoinColumn(name = "id_fabricante"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private List<Produto> produtos = new ArrayList<>();

    @Size(max = 14, message = "O CNPJ do fabricante não pode exceder 14 caracteres")
    @Column(length = 14, unique = true)
    private long cnpj;

    @Size(max = 50, message = "O nome do fabricante não pode exceder 50 caracteres")
    @Column(length = 50)
    private String nome;
}
