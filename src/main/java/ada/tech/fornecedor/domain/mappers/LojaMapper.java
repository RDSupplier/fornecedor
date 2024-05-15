package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.entities.Loja;

public class LojaMapper {

    public static Loja toEntity(LojaDto dto){
        return Loja.builder()
                .registroAnvisa(dto.getRegistroAnvisa())
                .cnpj(dto.getCnpj())
                .nomeUnidade(dto.getNomeUnidade())
                .inscricaoEstadual(dto.getInscricaoEstadual())
                .farmaceutico_responsavel(dto.getFarmaceutico_responsavel())
                .crf(dto.getCrf())
                .senha(dto.getSenha())
                .data_atualizacao(dto.getData_atualizacao())
                .build();
    }

    public static LojaDto toDto(Loja entity) {
        return LojaDto.builder()
                .registroAnvisa(entity.getRegistroAnvisa())
                .cnpj(entity.getCnpj())
                .nomeUnidade(entity.getNomeUnidade())
                .inscricaoEstadual(entity.getInscricaoEstadual())
                .farmaceutico_responsavel(entity.getFarmaceutico_responsavel())
                .crf(entity.getCrf())
                .senha(entity.getSenha())
                .data_atualizacao(entity.getData_atualizacao())
                .id(entity.getId())
                .build();
    }

}
