package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.entities.Fornecedor;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorDto dto){
        return Fornecedor.builder()
                .cnpj(dto.getCnpj())
                .nome(dto.getNome())
                .senha(dto.getSenha())
                .nire(dto.getNire())
                .build();
    }

    public static FornecedorDto toDto(Fornecedor entity) {
        return FornecedorDto.builder()
                .cnpj(entity.getCnpj())
                .nome(entity.getNome())
                .senha(entity.getSenha())
                .nire(entity.getNire())
                .id(entity.getId())
                .build();
    }
}
