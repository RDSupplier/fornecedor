package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.IFornecedorService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
    private final IFornecedorService fornecedorService;

    private final EstoqueController estoqueController;

    @Autowired
    public FornecedorController(IFornecedorService fornecedorService, EstoqueController estoqueController){
        this.fornecedorService = fornecedorService;
        this.estoqueController = estoqueController;
    }

    @PostMapping
    public ResponseEntity<?> criarFornecedor(
            @RequestBody FornecedorDto fornecedorDto
    ){
        try{
            FornecedorDto fornecedor = fornecedorService.criarFornecedor(fornecedorDto);

            EstoqueDto estoqueDto = new EstoqueDto();
            estoqueController.criarEstoque(estoqueDto);
            estoqueController.adicionarFornecedor(fornecedor.getId(), fornecedor.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fornecedor: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o fornecedor: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
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

    @GetMapping("/{id}/pedidos")
    public ResponseEntity<?> listarPedidos(
            @PathVariable("id") int id
    ) throws NotFoundException {
        return ResponseEntity.ok(fornecedorService.listarPedidos(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFornecedor(
            @PathVariable("id") int id,
            @RequestBody FornecedorDto fornecedorDto
    ) throws NotFoundException {
        try {
            final FornecedorDto fornecedor = fornecedorService.atualizarFornecedor(id, fornecedorDto);
            if (fornecedor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(fornecedor);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o fornecedor: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o fornecedor: violação de restrição de dados");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(
            @PathVariable("id") int id
    ) throws NotFoundException, Exception {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(
            @RequestParam String email,
            @RequestHeader String otp,
            @RequestBody String novaSenha
    ) {
        try {
            return ResponseEntity.ok(fornecedorService.redefinirSenha(email, otp, novaSenha));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o fornecedor: violação de integridade de dados");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao editar o fornecedor: violação de restrição de dados");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar a requisição");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de confirmação inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o usuário com email: " + email);
        }
    }
}
