package ada.tech.fornecedor.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String nomeComercial;
    private String principioAtivo;
    private String apresentacao;
    private String lote;
    private LocalDate dataFabricacao;
    private String fabricante;
    private String fornecedor;
    private double preco;
    private boolean cargaPerigosa;
    private double volume;
    private int id;
}
