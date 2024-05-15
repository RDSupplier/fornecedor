package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.Fornecedor;

public class EstoqueMapper {
    public static Estoque toEntity(EstoqueDto dto) {
        Fornecedor fornecedor = FornecedorMapper.toEntity(dto.getFornecedor());

        return Estoque.builder()
                .id(dto.getId())
                .fornecedor(fornecedor)
                .produtos(ProdutoMapper.toEntityList(dto.getProdutos()))
                .quantidade(dto.getQuantidade())
                .build();
    }

    public static EstoqueDto toDto(Estoque entity) {
        FornecedorDto fornecedor = FornecedorMapper.toDto(entity.getFornecedor());

        return EstoqueDto.builder()
                .id(entity.getId())
                .fornecedor(fornecedor)
                .produtos(ProdutoMapper.toDtoList(entity.getProdutos()))
                .quantidade(entity.getQuantidade())
                .build();
    }
}
