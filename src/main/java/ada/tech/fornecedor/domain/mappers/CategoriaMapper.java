package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.entities.Categoria;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaDto dto) {
        if(dto == null) {
            return null;
        }

        return Categoria.builder()
                .categoria(dto.getCategoria())
                .build();
    }

    public static CategoriaDto toDto(Categoria entity) {
        if(entity == null) {
            return  null;
        }

        return CategoriaDto.builder()
                .categoria(entity.getCategoria())
                .build();
    }

    public static List<Categoria> toEntityList(List<CategoriaDto> dtos) {
        if(dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(CategoriaMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<CategoriaDto> toDtoList(List<Categoria> entities) {
        if(entities == null) {
            return null;
        }

        return entities.stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    }
}
