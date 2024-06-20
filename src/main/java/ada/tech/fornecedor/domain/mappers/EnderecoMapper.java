package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EnderecoDto;
import ada.tech.fornecedor.domain.entities.Endereco;

public class EnderecoMapper {
    public static Endereco toEntity(EnderecoDto dto) {
        if(dto == null) {
            return null;
        }

        return Endereco.builder()
                .id(dto.getId())
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public static EnderecoDto toDto(Endereco entity) {
        if(entity == null) {
            return null;
        }

        return EnderecoDto.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }
}
