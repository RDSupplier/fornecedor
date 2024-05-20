package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.ProdutoEstoqueDto;
import ada.tech.fornecedor.domain.entities.Estoque;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.entities.ProdutoEstoque;

import java.util.List;
import java.util.stream.Collectors;

public class EstoqueMapper {
    public static Estoque toEntity(EstoqueDto dto) {
        Fornecedor fornecedor = FornecedorMapper.toEntity(dto.getFornecedor());

        List<ProdutoEstoque> produtoEstoqueList = dto.getProdutos().stream()
                .map(ProdutoEstoqueMapper::toEntity)
                .collect(Collectors.toList());

        return Estoque.builder()
                .id(dto.getId())
                .fornecedor(fornecedor)
                .produtoEstoques(produtoEstoqueList)
                .build();
    }

    public static EstoqueDto toDto(Estoque entity) {
        FornecedorDto fornecedor = FornecedorMapper.toDto(entity.getFornecedor());

        List<ProdutoEstoqueDto> produtoEstoqueDtoList = entity.getProdutoEstoques().stream()
            .map(ProdutoEstoqueMapper::toDto)
            .collect(Collectors.toList());

            return EstoqueDto.builder()
            .id(entity.getId())
            .fornecedor(fornecedor)
            .produtos(produtoEstoqueDtoList)
            .build();

    }
}
