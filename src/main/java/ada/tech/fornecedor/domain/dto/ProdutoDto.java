package ada.tech.fornecedor.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ProdutoDto {
    @Positive
    @NotNull
    private int id;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O nome comercial do produto não pode exceder 50 caracteres")
    private String nomeComercial;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O princípio ativo não pode exceder 50 caracteres")
    private String principioAtivo;

    @NotBlank
    @NotEmpty
    @Size(max = 100, message = "A apresentação do produto não pode exceder 100 caracteres")
    private String apresentacao;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O lote não pode exceder 50 caracteres")
    private String lote;

    private LocalDate dataFabricacao;

    @PositiveOrZero
    private double preco;

    private boolean cargaPerigosa;

    @Positive
    @NotNull
    private double volume;
}
