package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="estoque_produto")
public class EstoqueProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @PositiveOrZero
    private int quantidade;

    @PositiveOrZero
    private int quantidadeReservada;

    public int reservarQuantidadeParcial(int quantidade) {
        int quantidadeDisponivel = this.quantidade - this.quantidadeReservada;
        int quantidadeReservada = Math.min(quantidade, quantidadeDisponivel);
        this.quantidadeReservada += quantidadeReservada;
        return quantidadeReservada;
    }

    public void confirmarReserva(int quantidade) {
        if (quantidade <= this.quantidadeReservada) {
            this.quantidadeReservada -= quantidade;
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade a liberar excede a quantidade reservada.");
        }
    }

    public void cancelarReserva(int quantidadeAtendida, String statusPedido) {
        if ("cancelado".equalsIgnoreCase(statusPedido)) {
            this.quantidade += quantidadeAtendida;
        } else {
            this.quantidadeReservada -= quantidadeAtendida;
        }
    }

    private void validarQuantidadeReservada(int quantidade) {
        if (quantidade > this.quantidadeReservada) {
            throw new IllegalStateException("A quantidade a confirmar excede a quantidade reservada.");
        }
    }
}
