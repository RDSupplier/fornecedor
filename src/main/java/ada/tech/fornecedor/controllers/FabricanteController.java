package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.IFabricanteService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/produto/fabricante")
public class FabricanteController {

    private final IFabricanteService fabricanteService;

    @Autowired
    public FabricanteController(IFabricanteService fabricanteService) {
        this.fabricanteService = fabricanteService;
    }

    @PostMapping
    public ResponseEntity<?> criarFabricante(
            @RequestBody FabricanteDto fabricanteDto
    ) throws NotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(fabricanteService.criarFabricante(fabricanteDto));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fabricante: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fabricante: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @GetMapping
    public ResponseEntity<List<FabricanteDto>> listarFabricantes() throws NotFoundException {
        return ResponseEntity.ok(fabricanteService.listarFabricantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FabricanteDto> listarProduto(
            @PathVariable("id") int id
    ) throws NotFoundException {
        return ResponseEntity.ok(fabricanteService.listarFabricante(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable("id") int id,
            @RequestBody FabricanteDto fabricanteDto
    ) throws NotFoundException {
        try {
            final FabricanteDto fabricante = fabricanteService.atualizarFabricante(id, fabricanteDto);

            if (fabricante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(fabricante);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fabricante: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fabricante: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(
            @PathVariable("id") int id
    ) throws NotFoundException {
        try {
            fabricanteService.deletarFabricante(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
