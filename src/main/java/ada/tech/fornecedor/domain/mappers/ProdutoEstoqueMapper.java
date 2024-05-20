package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.dto.ProdutoEstoqueDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.Produto;
import ada.tech.fornecedor.domain.entities.ProdutoEstoque;


public class ProdutoEstoqueMapper {
    public static ProdutoEstoque toEntity(ProdutoDto produtoDto) {
        Produto produto = ProdutoMapper.toEntity(produtoDto);
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setProdutos(produto);

        return produtoEstoque;
    }

    public static ProdutoEstoque toEntity(EstoqueDto estoqueDto) {
        Estoque estoque = EstoqueMapper.toEntity(estoqueDto);
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setEstoques(estoque);

        return produtoEstoque;
    }

    public static ProdutoEstoque toEntity(ProdutoDto produtoDto, EstoqueDto estoqueDto) {
        Produto produto = ProdutoMapper.toEntity(produtoDto);
        Estoque estoque = EstoqueMapper.toEntity(estoqueDto);
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setProdutos(produto);
        produtoEstoque.setEstoques(estoque);

        return produtoEstoque;
    }

    public static ProdutoEstoque toEntity(ProdutoEstoqueDto dto) {
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setQuantidade(dto.getQuantidade());

        return produtoEstoque;
    }

    public static ProdutoEstoqueDto toDto(Produto entity) {
        return ProdutoEstoqueDto.builder()
                .produtos(entity != null ? entity.getId() : null)
                .build();
    }

    public static ProdutoEstoqueDto toDto(Estoque entity) {
        return ProdutoEstoqueDto.builder()
                .estoques(entity != null ? entity.getId() : null)
                .build();
    }

    public static ProdutoEstoqueDto toDto(Produto produtoEntity, Estoque estoqueEntity) {
        return ProdutoEstoqueDto.builder()
                .produtos(produtoEntity != null ? produtoEntity.getId() : null)
                .estoques(estoqueEntity != null ? estoqueEntity.getId() : null)
                .build();
    }

    public static ProdutoEstoqueDto toDto(ProdutoEstoque entity) {
        if (entity == null || entity.getEstoques() == null) {
            return null;
        }

        Integer produtoId = entity.getId();
        Integer estoqueId = entity.getEstoques().getId();
        Integer quantidade = entity.getQuantidade();

        return ProdutoEstoqueDto.builder()
                .produtos(produtoId)
                .estoques(estoqueId)
                .quantidade(quantidade)
                .build();
    }
}
