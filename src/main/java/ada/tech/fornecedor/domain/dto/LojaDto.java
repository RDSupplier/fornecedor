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
public class LojaDto {
    @Positive
    @NotNull
    private int id;

    @Positive(message = "O Registro Anvisa da loja deve ser um número positivo")
    @NotNull
    @Size(max = 13, message = "O Registro Anvisa da loja não pode exceder 13 caracteres")
    private long registroAnvisa;

    @Positive(message = "O CNPJ da loja deve ser um número positivo")
    @NotNull
    @Size(max = 14, message = "O CNPJ da loja não pode exceder 14 caracteres")
    private long cnpj;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O nome da unidade não pode exceder 50 caracteres")
    private String nomeUnidade;

    @Positive(message = "A Inscrição Estadual da loja deve ser um número positivo")
    @NotNull
    @Size(max = 9, message = "A Inscrição Estadual da loja não pode exceder 9 caracteres")
    private int inscricaoEstadual;

    @NotBlank
    @NotEmpty
    @Size(max = 50, message = "O nome do farmacêutico responsável não pode exceder 50 caracteres")
    private String farmaceutico;

    @Positive(message = "O CRF deve ser um número positivo")
    @NotNull
    @Size(max = 13, message = "O CRF não pode exceder 5 caracteres")
    private int crf;

    @NotBlank
    @NotEmpty
    private String senha;

    private LocalDate dataAtualizacaoDados;
}
