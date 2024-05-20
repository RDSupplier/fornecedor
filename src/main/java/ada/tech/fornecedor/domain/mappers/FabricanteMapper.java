package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.entities.Fabricante;

public class FabricanteMapper {
    public static Fabricante toEntity(FabricanteDto dto) {
        if(dto == null) {
            return null;
        }

        return Fabricante.builder()
                .nome(dto.getNome())
                .cnpj(dto.getCnpj())
                .build();
    }

    public static FabricanteDto toDto(Fabricante entity) {
        if(entity == null) {
            return null;
        }

        return FabricanteDto.builder()
                .nome(entity.getNome())
                .cnpj(entity.getCnpj())
                .build();
    }
}
