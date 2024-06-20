package ada.tech.fornecedor.domain.mappers;

import ada.tech.fornecedor.domain.dto.*;
import ada.tech.fornecedor.domain.entities.Pedido;
import ada.tech.fornecedor.domain.entities.PedidoProduto;
import ada.tech.fornecedor.domain.entities.Produto;

import java.util.stream.Collectors;

public class PedidoMapper {

    public static Pedido toEntity(PedidoDto dto){
        return Pedido.builder()
                .data(dto.getData())
                .horario(dto.getHorario())
                .total(dto.getTotal())
                .volumeTotal(dto.getVolumeTotal())
                .status(dto.getStatus())
                .build();
    }

    public static PedidoDto toDto(Pedido entity) {
        PedidoDto pedidoDto = PedidoDto.builder()
                .data(entity.getData())
                .horario(entity.getHorario())
                .total(entity.getTotal())
                .volumeTotal(entity.getVolumeTotal())
                .fornecedor(entity.getFornecedor().getId())
                .id(entity.getId())
                .produtos(entity.getPedidoProduto().stream()
                        .map(PedidoMapper::toPedidoProdutoDto)
                        .collect(Collectors.toList()))
                .status(entity.getStatus())
                .build();

        EnderecoDto enderecoDto = new EnderecoDto();

        if (entity.getEndereco() != null) {
            enderecoDto.setRua(entity.getEndereco().getRua());
            enderecoDto.setNumero(entity.getEndereco().getNumero());
            enderecoDto.setComplemento(entity.getEndereco().getComplemento());
            enderecoDto.setBairro(entity.getEndereco().getBairro());
            enderecoDto.setCidade(entity.getEndereco().getCidade());
            enderecoDto.setEstado(entity.getEndereco().getEstado());
            enderecoDto.setCep(entity.getEndereco().getCep());
        }

        pedidoDto.setEndereco(enderecoDto);

        return pedidoDto;
    }
    private static PedidoProdutoDto toPedidoProdutoDto(PedidoProduto pedidoProduto) {
        Produto produto = pedidoProduto.getProdutos();
        return PedidoProdutoDto.builder()
                .id(produto.getId())
                .quantidade(pedidoProduto.getQuantidade())
                .volumeTotal(pedidoProduto.getVolumeTotal())
                .produto(ProdutoDto.builder()
                        .id(produto.getId())
                        .nomeComercial(produto.getNomeComercial())
                        .principioAtivo(produto.getPrincipioAtivo())
                        .apresentacao(produto.getApresentacao())
                        .preco(produto.getPreco())
                        .cargaPerigosa(produto.isCargaPerigosa())
                        .volume(produto.getVolume())
                        .imagem(produto.getImagem())
                        .codigoBarras(produto.getCodigoBarras())
                        .fabricante(produto.getFabricante() != null ? FabricanteDto.builder()
                                .nome(produto.getFabricante().getNome())
                                .cnpj(produto.getFabricante().getCnpj())
                                .build() : null)
                        .categorias(produto.getCategorias().stream()
                                .map(categoria -> CategoriaDto.builder()
                                        .id(categoria.getId())
                                        .categoria(categoria.getCategoria())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }
}
