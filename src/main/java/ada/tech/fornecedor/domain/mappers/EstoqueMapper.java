package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.EstoqueProdutoDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.EstoqueProduto;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.entities.Produto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EstoqueMapper {
    public static Estoque toEntity(EstoqueDto dto) {
        if(dto == null) {
            return null;
        }

        Fornecedor fornecedor = FornecedorMapper.toEntity(dto.getFornecedor());

        List<EstoqueProduto> estoqueProdutos = dto.getEstoqueProdutos() != null ? dto.getEstoqueProdutos()
                .stream()
                .map(EstoqueProdutoMapper::toEntity)
                .collect(Collectors.toList()) : Collections.emptyList();

        Estoque estoque = Estoque.builder()
                .id(dto.getId())
                .fornecedor(fornecedor)
                .estoqueProdutos(estoqueProdutos)
                .build();

        for(EstoqueProduto estoqueProduto : estoqueProdutos) {
            estoqueProduto.setEstoque(estoque);
        }

        return estoque;
    }

    public static EstoqueDto toDto(Estoque entity) {
        if(entity == null) {
            return null;
        }

        FornecedorDto fornecedor = FornecedorMapper.toDto(entity.getFornecedor());

        List<EstoqueProdutoDto> estoqueProdutoDtos = entity.getEstoqueProdutos() != null ? entity.getEstoqueProdutos()
                .stream()
                .map(EstoqueProdutoMapper::toDto)
                .collect(Collectors.toList()) : Collections.emptyList();

        return EstoqueDto.builder()
            .id(entity.getId())
            .fornecedor(fornecedor)
            .estoqueProdutos(estoqueProdutoDtos)
            .build();
    }
}
