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
@Table(name="tb_endereco")

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "enderecos")
    private List<Loja> lojas = new ArrayList<>();

    @OneToMany(mappedBy = "enderecos")
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @Size(max = 50, message = "O nome da rua não pode exceder 50 caracteres")
    @Column(length = 50)
    private String rua;

    @Size(max = 10, message = "O número de endereço não pode exceder 10 caracteres")
    @Column(length = 10)
    private String numero;

    @Size(max = 30, message = "O complemento da rua não pode exceder 30 caracteres")
    @Column(length = 30)
    private String complemento;

    @Size(max = 40, message = "O nome do bairro não pode exceder 40 caracteres")
    @Column(length = 40)
    private String bairro;

    @Size(max = 30, message = "O nome da cidade não pode exceder 30 caracteres")
    @Column(length = 30)
    private String cidade;

    @Size(max = 2, message = "A sigla do estado não pode exceder 2 caracteres")
    @Column(length = 2)
    private String estado;

    @Size(max = 8, message = "O CEP não pode exceder 8 caracteres")
    @Column(length = 8)
    private int cep;
}
