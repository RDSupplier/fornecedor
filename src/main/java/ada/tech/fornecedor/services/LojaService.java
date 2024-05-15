package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Endereco;
import ada.tech.fornecedor.domain.entities.Loja;
import ada.tech.fornecedor.domain.mappers.EnderecoMapper;
import ada.tech.fornecedor.domain.mappers.LojaMapper;
import ada.tech.fornecedor.repositories.ILojaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LojaService implements ILojaService{

    private final ILojaRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public LojaDto criarLoja(LojaDto lojaDto) {
        Endereco endereco = EnderecoMapper.toEntity(lojaDto.getEndereco());

        entityManager.persist(endereco);

        Loja loja = LojaMapper.toEntity(lojaDto);
        loja.setEnderecos(endereco);
        loja = repository.save(loja);
        return LojaMapper.toDto(loja);
    }

    @Override
    public List<LojaDto> listarLojas() {
        return repository.findAll().stream().map(LojaMapper::toDto).toList();
    }

    @Override
    public LojaDto listarLoja(int id) throws NotFoundException {
        return LojaMapper.toDto(findLojaById(id));
    }

    @Override
    public LojaDto atualizarLoja(int id, LojaDto pedido) throws NotFoundException {
        final Loja loja = repository.findById(id).orElseThrow(() -> new NotFoundException(Loja.class, String.valueOf(id)));
        loja.setRegistroAnvisa(pedido.getRegistroAnvisa());
        loja.setCnpj(pedido.getCnpj());
        loja.setNomeUnidade(pedido.getNomeUnidade());

        loja.setInscricaoEstadual(pedido.getInscricaoEstadual());
        loja.setFarmaceutico_responsavel(pedido.getFarmaceutico_responsavel());
        loja.setCrf(pedido.getCrf());
        loja.setSenha(pedido.getSenha());
        loja.setData_atualizacao(pedido.getData_atualizacao());
        return LojaMapper.toDto(repository.save(loja));
    }

    @Override
    public void deletarLoja(int id) throws NotFoundException{
        repository.deleteById(id);
    }

    private Loja findLojaById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Loja.class, String.valueOf(id)));
    }
}
