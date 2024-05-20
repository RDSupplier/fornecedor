package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.ProdutoDto;
import ada.tech.fornecedor.domain.entities.Fabricante;
import ada.tech.fornecedor.domain.entities.Produto;

import java.util.*;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoDto dto){
        if(dto == null) {
            return null;
        }

        Fabricante fabricante = FabricanteMapper.toEntity(dto.getFabricante());

        return Produto.builder()
                .nomeComercial(dto.getNomeComercial())
                .principioAtivo(dto.getPrincipioAtivo())
                .apresentacao(dto.getApresentacao())
                .lote(dto.getLote())
                .dataFabricacao(dto.getDataFabricacao())
                .categorias(CategoriaMapper.toEntityList(dto.getCategorias()))
                .fabricante(fabricante)
                .preco(dto.getPreco())
                .cargaPerigosa(dto.isCargaPerigosa())
                .volume(dto.getVolume())
                .imagem(dto.getImagem())
                .codigoBarras(dto.getCodigoBarras())
                .build();
    }

    public static ProdutoDto toDto(Produto entity) {
        if(entity == null) {
            return null;
        }

        FabricanteDto fabricante = FabricanteMapper.toDto(entity.getFabricante());

        return ProdutoDto.builder()
                .nomeComercial(entity.getNomeComercial())
                .principioAtivo(entity.getPrincipioAtivo())
                .apresentacao(entity.getApresentacao())
                .lote(entity.getLote())
                .dataFabricacao(entity.getDataFabricacao())
                .categorias(CategoriaMapper.toDtoList(entity.getCategorias()))
                .fabricante(fabricante)
                .preco(entity.getPreco())
                .cargaPerigosa(entity.isCargaPerigosa())
                .volume(entity.getVolume())
                .imagem(entity.getImagem())
                .codigoBarras(entity.getCodigoBarras())
                .build();
    }

    public static List<Produto> toEntityList(List<ProdutoDto> dtos) {
        if(dtos == null) {
            return null;
        }

        List<Produto> produtos = new ArrayList<>();

        for(ProdutoDto dto : dtos) {
            Produto produto = ProdutoMapper.toEntity(dto);
            produtos.add(produto);
        }

        return produtos;
    }

    public static List<ProdutoDto> toDtoList(List<Produto> entities) {
        if(entities == null) {
            return null;
        }

        List<ProdutoDto> produtos = new ArrayList<>();

        for(Produto entity : entities) {
            ProdutoDto produto = ProdutoMapper.toDto(entity);
            produtos.add(produto);
        }

        return produtos;
    }
}
