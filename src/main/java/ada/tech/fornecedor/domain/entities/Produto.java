package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_produto")

public class Produto {
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
