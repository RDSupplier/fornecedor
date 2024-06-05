package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.entities.Pedido;
import ada.tech.fornecedor.external.ILogisticaApi;
import ada.tech.fornecedor.services.LogisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fornecedor/logistica")
public class LogisticaController {
    private final LogisticaService logisticaService;

    @Autowired
    ILogisticaApi logisticaApi;

    public LogisticaController(LogisticaService logisticaService) {
        this.logisticaService = logisticaService;
    }

    @PostMapping("/dados-logistica")
    public ResponseEntity<String> receberDadosLogistica(@RequestBody Pedido pedido) {
        logisticaService.enviarDadosLogistica(pedido);
        return new ResponseEntity<>("Dados Enviados!", HttpStatus.OK);
    }

    @PostMapping("https://crudcrud.com/api/55bae12b3cc74fe9af1dcf6863dc4645/logistica")
    public ResponseEntity<String> receberDadosLogisticaTeste(@RequestBody Pedido pedido) {
        logisticaService.enviarDadosLogistica(pedido);
        return new ResponseEntity<>("Dados Enviados!", HttpStatus.OK);
    }
}
