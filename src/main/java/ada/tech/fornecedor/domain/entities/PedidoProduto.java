package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedido_produto")

public class PedidoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedidos;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produtos;

    @Size(max = 20, message = "A quantidade de produtos do pedido não pode exceder 20 caracteres")
    @Column(length = 20)
    private int quantidade;

    @Column(name = "volume_total", precision = 10)
    private double volumeTotal;
}
