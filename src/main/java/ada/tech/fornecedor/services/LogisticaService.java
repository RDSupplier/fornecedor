package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.entities.Endereco;
import ada.tech.fornecedor.domain.entities.Pedido;
import org.springframework.stereotype.Service;


@Service
public class LogisticaService {
    public String enviarDadosLogistica(Pedido pedido) {
        String jsonLogistica = criarJsonLogistica(pedido);
        System.out.println("Enviando dados para o módulo de logística: " + jsonLogistica);
        return jsonLogistica;
    }

    private String criarJsonLogistica(Pedido pedido) {
        Endereco enderecoOrigem = pedido.getFornecedor().getEnderecos();
        Endereco enderecoDestino = mockEnderecoDestino();

        return String.format(
                "{ \"id_pedido\": %d, \"data_pedido\": \"%s\", \"id_fornecedor\": \"%s\", " +
                        "\"endereco_origem\": { \"rua\": \"%s\", \"numero\": \"%s\", \"cidade\": \"%s\", \"estado\": \"%s\", \"cep\": \"%s\" }, " +
                        "\"endereco_destino\": { \"rua\": \"%s\", \"numero\": \"%s\", \"cidade\": \"%s\", \"estado\": \"%s\", \"cep\": \"%s\" } }",
                pedido.getId(), pedido.getData().toString(), pedido.getFornecedor().getId(),
                enderecoOrigem.getRua(), enderecoOrigem.getNumero(), enderecoOrigem.getCidade(), enderecoOrigem.getEstado(), enderecoOrigem.getCep(),
                enderecoDestino.getRua(), enderecoDestino.getNumero(), enderecoDestino.getCidade(), enderecoDestino.getEstado(), enderecoDestino.getCep()
        );
    }

    private Endereco mockEnderecoDestino() {
        return Endereco.builder()
                .rua("Avenida Paulista")
                .numero("138")
                .cidade("São Paulo")
                .estado("SP")
                .cep(11320180)
                .build();
    }
}
