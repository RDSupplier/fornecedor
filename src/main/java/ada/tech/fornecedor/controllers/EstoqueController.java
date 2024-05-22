package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.ProdutoEstoque;
import ada.tech.fornecedor.services.IEstoqueService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/estoque")
public class EstoqueController {
    private final IEstoqueService estoqueService;

    @Autowired
    public EstoqueController(IEstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<?> criarEstoque(
            @Valid
            @RequestBody EstoqueDto estoqueDto
    ) {
        try {
            EstoqueDto estoque = estoqueService.criarEstoque(estoqueDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(estoque);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o estoque: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o estoque: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @GetMapping()
    public ResponseEntity<List<EstoqueDto>> listarEstoques() {
        return ResponseEntity.ok(estoqueService.listarEstoques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDto> listarEstoque(
            @PathVariable int id
    ) throws NotFoundException {
        return ResponseEntity.ok(estoqueService.listarEstoque(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEstoque(
            @PathVariable int id,
            @RequestBody EstoqueDto estoqueDto
    ) throws NotFoundException {
        try {
            final EstoqueDto estoque = estoqueService.atualizarEstoque(id, estoqueDto);

            if (estoque == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(estoque);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o estoque: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o estoque: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstoque(
            @PathVariable int id
    ) throws NotFoundException {
        estoqueService.deletarEstoque(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/produto")
    public ResponseEntity<?> adicionarProduto(
            @Valid
            @PathVariable int id,
            @RequestBody ProdutoEstoque produtoEstoque
    ) throws NotFoundException {
        try {
            final EstoqueDto estoque = estoqueService.adicionarProduto(id, produtoEstoque);

            if(estoque == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(estoque);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar produto ao estoque: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar produto ao estoque: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @PatchMapping("/{id}/fornecedor")
    public ResponseEntity<?> adicionarFornecedor(
            @Valid
            @PathVariable int id,
            @RequestBody FornecedorDto fornecedorDto
    ) {
        try {
            final EstoqueDto estoque = estoqueService.adicionarFornecedor(id, fornecedorDto);

            if(estoque == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(estoque);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar produto ao estoque: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar produto ao estoque: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }
}
