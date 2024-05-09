package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Loja;
import ada.tech.fornecedor.services.ILojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/loja")
public class LojaController {
    private final ILojaService servico;

    @Autowired
    public LojaController(ILojaService servico){
        this.servico = servico;
    }

    @PostMapping
    public ResponseEntity<LojaDto> criarLoja(
            @RequestBody LojaDto pedido
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(servico.criarLoja(pedido));
    }

    @GetMapping
    public ResponseEntity<List<LojaDto>> listarLojas(){
        return ResponseEntity.ok(servico.listarLojas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaDto> listarLoja(
            @PathVariable("id") int id
    )  throws NotFoundException {
        return ResponseEntity.ok(servico.listarLoja(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaDto> atualizarLoja(
            @PathVariable("id") int id,
            @RequestBody LojaDto pedido
    ) throws NotFoundException {
        final LojaDto album = servico.atualizarLoja(id, pedido);
        if (album == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(album);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLoja(
            @PathVariable("id") int id
    ) throws NotFoundException {
        servico.deletarLoja(id);
        return ResponseEntity.noContent().build();
    }


}
