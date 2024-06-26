package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.PedidoProdutoDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.entities.Fabricante;
import ada.tech.fornecedor.domain.entities.Pedido;
import ada.tech.fornecedor.domain.entities.PedidoProduto;
import ada.tech.fornecedor.domain.entities.Produto;

public class PedidoProdutoMapper {
    public static PedidoProduto toEntity(PedidoProdutoDto dto){
        if(dto == null) {
            return null;
        }

        return PedidoProduto.builder()
                .id(dto.getId())
                .quantidade(dto.getQuantidade())
                .quantidadeAtendida(dto.getQuantidadeAtendida())
                .volumeTotal(dto.getVolumeTotal())
                .produtos(ProdutoMapper.toEntity(dto.getProduto()))
                .build();
    }

    public static PedidoProdutoDto toDto(PedidoProduto entity) {
        if(entity == null) {
            return null;
        }

        return PedidoProdutoDto.builder()
                .id(entity.getId())
                .produto(ProdutoMapper.toDto(entity.getProdutos()))
                .quantidade(entity.getQuantidade())
                .quantidadeAtendida(entity.getQuantidadeAtendida())
                .volumeTotal(entity.getVolumeTotal())
                .build();
    }
}
