package ada.tech.fornecedor.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class PedidoDto {
    @Positive
    @NotNull
    private int id;

    private LocalDate data;

    private LocalTime horario;

    private int fornecedor;

    private EnderecoDto endereco;

    @Positive
    @NotNull
    private double total;

    @Positive
    @NotNull
    private double volumeTotal;

    private List<PedidoProdutoDto> produtos;
}
