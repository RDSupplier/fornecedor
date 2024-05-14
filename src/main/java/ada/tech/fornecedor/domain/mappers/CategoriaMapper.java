package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.entities.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaDto dto) {
        return Categoria.builder()
                .id(dto.getId())
                .categoria(dto.getCategoria())
                .build();
    }

    public static CategoriaDto toDto(Categoria entity) {
        return CategoriaDto.builder()
                .id(entity.getId())
                .categoria(entity.getCategoria())
                .build();
    }

    public static List<Categoria> toEntityList(List<CategoriaDto> dtos) {
        return dtos.stream()
                .map(CategoriaMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<CategoriaDto> toDtoList(List<Categoria> entities) {
        return entities.stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    }
}
