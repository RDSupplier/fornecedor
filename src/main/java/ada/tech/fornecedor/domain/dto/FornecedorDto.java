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
public class FornecedorDto {
    @Positive
    @NotNull
    private int id;

    @Positive
    @NotNull
    @Size(max = 14, message = "O CNPJ do fornecedor não pode exceder 14 caracteres")
    private long cnpj;

    @Positive
    @NotNull
    @Size(max = 11, message = "O NIRE do fornecedor não pode exceder 11 caracteres")
    private long nire;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O nome do fornecedor não pode exceder 50 caracteres")
    private String nome;


    private String senha;
}
