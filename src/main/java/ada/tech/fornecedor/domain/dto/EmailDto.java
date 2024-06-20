package ada.tech.fornecedor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String destinatario;
    private String assunto;
    private String mensagem;
}
