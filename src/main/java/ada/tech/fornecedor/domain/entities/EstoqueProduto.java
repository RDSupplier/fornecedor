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

    public void liberarReserva(int quantidade) {
        System.out.println("quantidade: " + quantidade);
        if (quantidade <= this.quantidadeReservada) {
            this.quantidadeReservada -= quantidade;
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade a liberar excede a quantidade reservada.");
        }
    }

    public void confirmarReserva(int quantidade) {
        validarQuantidadeReservada(quantidade);
        this.quantidade -= quantidade;
        this.quantidadeReservada -= quantidade;
    }

    private void validarQuantidadeReservada(int quantidade) {
        if (quantidade > this.quantidadeReservada) {
            throw new IllegalStateException("A quantidade a confirmar excede a quantidade reservada.");
        }
    }
}
