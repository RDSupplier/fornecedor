package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(max = 13, message = "O registro anvisa da loja não pode exceder 13 caracteres")
    @Column(name = "registro_anvisa", unique = true, length = 13)
    private long registroAnvisa;

    @Size(max = 14, message = "O CNPJ da loja não pode exceder 14 caracteres")
    @Column(unique = true, length = 14)
    private long cnpj;

    @Size(max = 50, message = "O nome da unidade da loja não pode exceder 50 caracteres")
    @Column(name = "nome_unidade", length = 50)
    private String nomeUnidade;

    @Size(max = 9, message = "A inscrição estadual da loja não pode exceder 9 caracteres")
    @Column(name = "inscricao_estadual", length = 9)
    private int inscricaoEstadual;

    @Size(max = 50, message = "O nome do farmacêutico responsável pela loja não pode exceder 50 caracteres")
    @Column(name = "farmaceutico_responsavel", length = 50)
    private String farmaceutico;

    @Size(max = 5, message = "O CRF não pode exceder 5 caracteres")
    @Column(unique = true, length = 5)
    private int crf;

    private String senha;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacaoDados;
}
