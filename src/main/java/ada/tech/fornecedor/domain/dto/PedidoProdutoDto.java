package ada.tech.fornecedor.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoDto {
    @Positive
    @NotNull
    private int id;

    @Positive
    @NotNull
    private int quantidade;

    @Positive
    @NotNull
    private double volumeTotal;

    private ProdutoDto produto;
}
