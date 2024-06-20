package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.entities.EstoqueProduto;

public class EstoqueProdutoMapper {
    public static EstoqueProduto toEntity(EstoqueProdutoDto dto) {
        if(dto == null) {
            return null;
        }

        return EstoqueProduto.builder()
                .id(dto.getId())
//                .estoque(EstoqueMapper.toEntity(dto.getEstoqueDto()))
                .produto(ProdutoMapper.toEntity(dto.getProdutoDto()))
                .quantidade(dto.getQuantidade())
                .build();
    }

    public static EstoqueProdutoDto toDto(EstoqueProduto entity) {
        if(entity == null) {
            return null;
        }

        return EstoqueProdutoDto.builder()
                .id(entity.getId())
//                .estoqueDto(EstoqueMapper.toDto(entity.getEstoque()))
                .produtoDto(ProdutoMapper.toDto(entity.getProduto()))
                .quantidade(entity.getQuantidade())
                .build();
    }
}
