package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.services.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/produto")
public class ProdutoController {

    private final IProdutoService produtoService;

    @Autowired
    public ProdutoController(IProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDto> criarProduto(
            @RequestBody ProdutoDto produtoDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criarProduto(produtoDto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> listarProduto(
            @PathVariable("id") int id
    ) throws NotFoundException {
        return ResponseEntity.ok(produtoService.listarProduto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> atualizarProduto(
            @PathVariable("id") int id,
            @RequestBody ProdutoDto produtoDto
    ) throws NotFoundException {
        final ProdutoDto produto = produtoService.atualizarProduto(id, produtoDto);
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(
            @PathVariable("id") int id
    ) throws NotFoundException {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
