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
@Table(name="tb_loja")

public class Loja {
    private int registroAnvisa;
    private String cnpj;
    private String nomeUnidade;
    private String endereco;
    private String inscricaoEstadual;
    private String farmaceutico;
    private String crf;
    private String senha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
