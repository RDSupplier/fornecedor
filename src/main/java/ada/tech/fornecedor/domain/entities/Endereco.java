package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
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
@Table(name="tb_endereco")

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "enderecos")
    private List<Loja> lojas = new ArrayList<>();

    @OneToMany(mappedBy = "enderecos")
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @Column(length = 50)
    private String rua;

    @Column(length = 10)
    private String numero;

    @Column(length = 30)
    private String complemento;

    @Column(length = 40)
    private String bairro;

    @Column(length = 30)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 8)
    private int cep;
}
