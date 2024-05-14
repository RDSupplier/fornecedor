package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.EnderecoDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.entities.Endereco;
import ada.tech.fornecedor.domain.entities.Fornecedor;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorDto dto){
        Endereco endereco = EnderecoMapper.toEntity(dto.getEndereco());

        return Fornecedor.builder()
                .cnpj(dto.getCnpj())
                .nome(dto.getNome())
                .senha(dto.getSenha())
                .nire(dto.getNire())
                .enderecos(endereco)
                .build();
    }

    public static FornecedorDto toDto(Fornecedor entity) {
        EnderecoDto enderecoDto = EnderecoMapper.toDto(entity.getEnderecos());

        return FornecedorDto.builder()
                .cnpj(entity.getCnpj())
                .nome(entity.getNome())
                .senha(entity.getSenha())
                .nire(entity.getNire())
                .id(entity.getId())
                .endereco(enderecoDto)
                .build();
    }
}
