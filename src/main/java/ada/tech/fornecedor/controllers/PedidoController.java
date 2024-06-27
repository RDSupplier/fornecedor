package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/pedido")
public class PedidoController {
    private final IPedidoService pedidoService;

    @Autowired
    public PedidoController(IPedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(
            @RequestBody PedidoDto pedidoDto
    ){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(pedidoDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> listarPedidos(){
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> listarPedido(
            @PathVariable("id") int id
    )  throws NotFoundException {
        return ResponseEntity.ok(pedidoService.listarPedido(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedido(
            @PathVariable("id") int id,
            @RequestBody PedidoDto pedidoDto
    ) throws NotFoundException {
        try {
            final PedidoDto pedido = pedidoService.atualizarPedido(id, pedidoDto);
            return ResponseEntity.ok(pedido);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(
            @PathVariable("id") int id
    ) throws NotFoundException {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{id}/distancia")
//    public ResponseEntity<String> calcularDistanciaPedido(
//            @PathVariable("id") int id
//    ) throws NotFoundException {
//        String distancia = String.valueOf(calcularDistanciaPedido(id));
//        return ResponseEntity.ok(distancia);
//    }
}
