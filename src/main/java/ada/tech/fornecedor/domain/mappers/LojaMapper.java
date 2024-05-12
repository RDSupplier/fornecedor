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
                .farmaceutico(dto.getFarmaceutico())
                .crf(dto.getCrf())
                .senha(dto.getSenha())
                .dataAtualizacaoDados(dto.getDataAtualizacaoDados())
                .build();
    }

    public static LojaDto toDto(Loja entity) {
        return LojaDto.builder()
                .registroAnvisa(entity.getRegistroAnvisa())
                .cnpj(entity.getCnpj())
                .nomeUnidade(entity.getNomeUnidade())
                .inscricaoEstadual(entity.getInscricaoEstadual())
                .farmaceutico(entity.getFarmaceutico())
                .crf(entity.getCrf())
                .senha(entity.getSenha())
                .dataAtualizacaoDados(entity.getDataAtualizacaoDados())
                .id(entity.getId())
                .build();
    }

}
