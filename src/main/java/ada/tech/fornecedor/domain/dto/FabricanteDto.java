package ada.tech.fornecedor.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class FabricanteDto {
    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O nome do fabricante não pode exceder 50 caracteres")
    private String nome;

    @Positive
    @NotNull
    @Size(max = 14, message = "O CNPJ do fabricante não pode exceder 14 caracteres")
    private long cnpj;
}
