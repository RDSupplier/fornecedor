package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.IFornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
    private final IFornecedorService fornecedorService;

    @Autowired
    public FornecedorController(IFornecedorService fornecedorService){
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorDto> criarFornecedor(
            @RequestBody FornecedorDto fornecedorDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.criarFornecedor(fornecedorDto));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDto>> listarFornecedores(){
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDto> listarFornecedor(
            @PathVariable("id") int id
    )  throws NotFoundException {
        return ResponseEntity.ok(fornecedorService.listarFornecedor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDto> atualizarFornecedor(
            @PathVariable("id") int id,
            @RequestBody FornecedorDto fornecedorDto
    ) throws NotFoundException {
        final FornecedorDto fornecedor = fornecedorService.atualizarFornecedor(id, fornecedorDto);
        if (fornecedor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(fornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(
            @PathVariable("id") int id
    ) throws NotFoundException {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
