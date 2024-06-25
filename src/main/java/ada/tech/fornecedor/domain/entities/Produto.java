package ada.tech.fornecedor.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoqueProdutos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    @OneToMany(mappedBy = "produtos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProduto> pedidoProduto = new ArrayList<>();

    @ManyToMany(mappedBy = "produtos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Categoria> categorias = new ArrayList<>();

    @Size(max = 50, message = "O nome comercial do produto não pode exceder 50 caracteres")
    @Column(name = "nome_comercial", length = 50)
    private String nomeComercial;

    @Size(max = 50, message = "O principio ativo do produto não pode exceder 50 caracteres")
    @Column(name = "principio_ativo", length = 50)
    private String principioAtivo;

    @Size(max = 100, message = "A apresentação do produto não pode exceder 100 caracteres")
    @Column(name = "apresentacao", length = 100)
    private String apresentacao;

    @Column(precision = 10)
    private double preco;

    @Column(name = "carga_perigosa")
    private boolean cargaPerigosa;

    @Column(precision = 10)
    private double volume;

    private String imagem;

    @Column(name = "codigo_barras", length = 13)
    @Size(max = 13, message = "O código de barras não exceder 13 caracteres")
    private String codigoBarras;

    @Size(max = 18, message = "O status do produto não pode exceder 18 caracteres")
    private String status;
}
