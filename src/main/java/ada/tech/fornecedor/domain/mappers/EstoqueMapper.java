package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.entities.Produto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EstoqueMapper {
    public static Estoque toEntity(EstoqueDto dto) {
        Fornecedor fornecedor = FornecedorMapper.toEntity(dto.getFornecedor());

        List<Produto> produtos = dto.getProdutos() != null ? dto.getProdutos()
                .stream()
                .map(ProdutoMapper::toEntity)
                .collect(Collectors.toList()) : Collections.emptyList();

        return Estoque.builder()
                .id(dto.getId())
                .fornecedor(fornecedor)
                .produtos(produtos)
                .build();
    }

    public static EstoqueDto toDto(Estoque entity) {
        FornecedorDto fornecedor = FornecedorMapper.toDto(entity.getFornecedor());

        List<ProdutoDto> produtos = entity.getProdutos() != null ? entity.getProdutos()
                .stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList()) : Collections.emptyList();

            return EstoqueDto.builder()
            .id(entity.getId())
            .fornecedor(fornecedor)
            .produtos(produtos)
            .build();
    }
}
