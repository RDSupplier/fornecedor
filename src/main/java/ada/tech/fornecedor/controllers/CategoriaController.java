package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.ICategoriaService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/produto/categoria")
public class CategoriaController {
    private final ICategoriaService categoriaService;

    @Autowired
    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(
            @RequestBody CategoriaDto categoriaDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criarCategoria(categoriaDto));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar a categoria: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar a categoria: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() throws NotFoundException {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> listarCategoria(
            @PathVariable int id
    ) throws NotFoundException {
        return ResponseEntity.ok(categoriaService.listarCategoria(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(
            @PathVariable int id,
            @RequestBody CategoriaDto categoriaDto
    ) throws NotFoundException {
        try {
            final CategoriaDto categoria = categoriaService.atualizarCategoria(id, categoriaDto);

            if(categoria == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(categoria);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o produto: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o produto: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(
            @PathVariable int id
    ) throws NotFoundException {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
