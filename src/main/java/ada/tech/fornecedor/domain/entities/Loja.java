package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_loja")

public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco enderecos;

    @Column(name = "registro_anvisa", unique = true, length = 13)
    private long registroAnvisa;

    @Column(unique = true, length = 14)
    private long cnpj;

    @Column(name = "nome_unidade", length = 50)
    private String nomeUnidade;

    @Column(name = "inscricao_estadual", length = 9)
    private int inscricaoEstadual;

    @Column(name = "farmaceutico_responsavel", length = 50)
    private String farmaceutico;

    @Column(unique = true, length = 5)
    private int crf;

    private String senha;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacaoDados;
}
