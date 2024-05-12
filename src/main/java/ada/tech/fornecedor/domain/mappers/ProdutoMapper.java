package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.entities.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoDto dto){
        return Produto.builder()
                .nomeComercial(dto.getNomeComercial())
                .principioAtivo(dto.getPrincipioAtivo())
                .apresentacao(dto.getApresentacao())
                .lote(dto.getLote())
                .dataFabricacao(dto.getDataFabricacao())

                .preco(dto.getPreco())
                .cargaPerigosa(dto.isCargaPerigosa())
                .volume(dto.getVolume())
                .build();
    }

    public static ProdutoDto toDto(Produto entity) {
        return ProdutoDto.builder()
                .nomeComercial(entity.getNomeComercial())
                .principioAtivo(entity.getPrincipioAtivo())
                .apresentacao(entity.getApresentacao())
                .lote(entity.getLote())
                .dataFabricacao(entity.getDataFabricacao())
                .preco(entity.getPreco())
                .cargaPerigosa(entity.isCargaPerigosa())
                .volume(entity.getVolume())
                .id(entity.getId())
                .build();
    }
}
