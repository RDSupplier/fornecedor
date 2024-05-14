package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.entities.Fabricante;

import java.util.List;
import java.util.stream.Collectors;

public class FabricanteMapper {
    public static Fabricante toEntity(FabricanteDto dto) {
        return Fabricante.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cnpj(dto.getCnpj())
                .build();
    }

    public static FabricanteDto toDto(Fabricante entity) {
        return FabricanteDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cnpj(entity.getCnpj())
                .build();
    }

    public static List<Fabricante> toEntityList(List<FabricanteDto> dtos) {
        return dtos.stream()
                .map(FabricanteMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<FabricanteDto> toDtoList(List<Fabricante> entities) {
        return entities.stream()
                .map(FabricanteMapper::toDto)
                .collect(Collectors.toList());
    }
}
