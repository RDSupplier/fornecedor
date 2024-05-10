package ada.tech.fornecedor.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
}
