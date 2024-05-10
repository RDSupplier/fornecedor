package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Loja;
import ada.tech.fornecedor.domain.mappers.LojaMapper;
import ada.tech.fornecedor.repositories.ILojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LojaService implements ILojaService{

    private final ILojaRepository repository;

    @Override
    public LojaDto criarLoja(LojaDto pedido) {
        Loja loja = LojaMapper.toEntity(pedido);
        return LojaMapper.toDto(repository.save(loja));
    }

    @Override
    public List<LojaDto> listarLojas() {
        return repository.findAll().stream().map(LojaMapper::toDto).toList();
    }

    @Override
    public LojaDto listarLoja(int id) throws NotFoundException {
        return LojaMapper.toDto(searchAlbumById(id));
    }

    @Override
    public LojaDto atualizarLoja(int id, LojaDto pedido) throws NotFoundException {
        final Loja loja = repository.findById(id).orElseThrow(() -> new NotFoundException(Loja.class, String.valueOf(id)));
        loja.setRegistroAnvisa(pedido.getRegistroAnvisa());
        loja.setCnpj(pedido.getCnpj());
        loja.setNomeUnidade(pedido.getNomeUnidade());
        loja.setEndereco(pedido.getEndereco());
        loja.setInscricaoEstadual(pedido.getInscricaoEstadual());
        loja.setFarmaceutico(pedido.getFarmaceutico());
        loja.setCrf(pedido.getCrf());
        loja.setSenha(pedido.getSenha());
        loja.setDataAtualizacaoDados(pedido.getDataAtualizacaoDados());
        return LojaMapper.toDto(repository.save(loja));
    }

    @Override
    public void deletarLoja(int id) throws NotFoundException{
        repository.deleteById(id);
    }

    private Loja searchAlbumById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Loja.class, String.valueOf(id)));
    }
}
