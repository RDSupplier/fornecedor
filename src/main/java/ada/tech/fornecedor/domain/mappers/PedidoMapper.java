package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.entities.Pedido;

public class PedidoMapper {

    public static Pedido toEntity(PedidoDto dto){
        return Pedido.builder()
                .data(dto.getData())
                .horario(dto.getHorario())
                .total(dto.getTotal())
                .volumeTotal(dto.getVolumeTotal())
                .build();
    }

    public static PedidoDto toDto(Pedido entity) {
        return PedidoDto.builder()
                .data(entity.getData())
                .horario(entity.getHorario())
                .total(entity.getTotal())
                .volumeTotal(entity.getVolumeTotal())
                .fornecedor(entity.getFornecedor().getId())
                .endereco(entity.getEndereco().getId())
                .id(entity.getId())
                .build();
    }
}
