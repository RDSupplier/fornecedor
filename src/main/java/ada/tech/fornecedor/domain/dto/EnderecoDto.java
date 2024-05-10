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
public class EnderecoDto {
    @Positive
    @NotNull
    private int id;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "A rua não pode exceder 50 caracteres")
    private String rua;

    @NotBlank
    @NotEmpty
    @Size(max = 10, message = "O número não pode exceder 10 caracteres")
    private String numero;

    @NotBlank
    @NotEmpty
    @Size(max = 30, message = "O complemento não pode exceder 30 caracteres")
    private String complemento;

    @NotBlank
    @NotEmpty
    @Size(max = 40, message = "O bairro não pode exceder 40 caracteres")
    private String bairro;

    @NotBlank
    @NotEmpty
    @Size(max = 30, message = "A cidade não pode exceder 30 caracteres")
    private String cidade;

    @NotBlank
    @NotEmpty
    @Size(max = 2, message = "O estado não pode exceder 2 caracteres")
    private String estado;

    @Positive
    @NotNull
    @Size(max = 8, message = "O CEP não pode exceder 8 caracteres")
    private int cep;
}
