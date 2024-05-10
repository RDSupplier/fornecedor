package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
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

    @Column(length = 20)
    private int quantidade;

    @Column(name = "volume_total", precision = 10, scale = 2)
    private double volumeTotal;
}
