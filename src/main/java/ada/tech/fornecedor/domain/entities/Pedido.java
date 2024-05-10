package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_pedido")

public class Pedido {
    private LocalDate data;
    private LocalTime horario;
    private double total;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
