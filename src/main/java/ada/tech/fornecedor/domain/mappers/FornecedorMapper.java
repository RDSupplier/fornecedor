package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.entities.Fornecedor;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorDto dto){
        return Fornecedor.builder()
                .cnpj(dto.getCnpj())
                .nome(dto.getNome())
                .senha(dto.getSenha())
                .build();
    }

    public static FornecedorDto toDto(Fornecedor entity) {
        return FornecedorDto.builder()
                .cnpj(entity.getCnpj())
                .nome(entity.getNome())
                .senha(entity.getSenha())
                .id(entity.getId())
                .build();
    }
}
