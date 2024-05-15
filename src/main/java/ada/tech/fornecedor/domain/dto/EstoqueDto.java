package ada.tech.fornecedor.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class EstoqueDto {
    @Positive
    @NotNull
    private int id;

    @PositiveOrZero
    @NotNull
    private int quantidade;

    @JsonProperty("fornecedor")
    private FornecedorDto fornecedor;

    @JsonProperty("produtos")
    private List<ProdutoDto> produtos = new ArrayList<>();
}
