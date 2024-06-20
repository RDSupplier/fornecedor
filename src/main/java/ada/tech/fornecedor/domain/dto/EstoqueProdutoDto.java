package ada.tech.fornecedor.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class EstoqueProdutoDto {
    @Positive
    @NotNull
    private int id;

    @NotNull
    @JsonProperty("produto")
    private ProdutoDto produtoDto;

    @PositiveOrZero
    @NotNull
    private int quantidade;
}
