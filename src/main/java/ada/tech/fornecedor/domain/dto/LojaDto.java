package ada.tech.fornecedor.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class LojaDto {
    private int registroAnvisa;
    private String cnpj;
    private String nomeUnidade;
    private String endereco;
    private String inscricaoEstadual;
    private String farmaceutico;
    private String crf;
    private String senha;
    private int id;
}
