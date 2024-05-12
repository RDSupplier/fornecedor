package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Endereco;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.mappers.FornecedorMapper;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService implements IFornecedorService {

    private final IFornecedorRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public FornecedorDto criarFornecedor(FornecedorDto fornecedorDto) {
        Endereco endereco = new Endereco();
        endereco.setRua(fornecedorDto.getEndereco().getRua());
        endereco.setNumero(fornecedorDto.getEndereco().getNumero());
        endereco.setComplemento(fornecedorDto.getEndereco().getComplemento());
        endereco.setBairro(fornecedorDto.getEndereco().getBairro());
        endereco.setCidade(fornecedorDto.getEndereco().getCidade());
        endereco.setEstado(fornecedorDto.getEndereco().getEstado());
        endereco.setCep(fornecedorDto.getEndereco().getCep());

        entityManager.persist(endereco);

        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);
        fornecedor.setEnderecos(endereco);
        fornecedor = repository.save(fornecedor);
        return FornecedorMapper.toDto(fornecedor);
    }

    @Override
    public List<FornecedorDto> listarFornecedores() {
        return repository.findAll().stream().map(FornecedorMapper::toDto).toList();
    }

    @Override
    public FornecedorDto listarFornecedor(int id) throws NotFoundException {
        return FornecedorMapper.toDto(searchFornecedorById(id));
    }

    @Override
    public FornecedorDto atualizarFornecedor(int id, FornecedorDto fornecedorDto) throws NotFoundException {
        final Fornecedor fornecedor = repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));
        fornecedor.setCnpj(fornecedorDto.getCnpj());
        fornecedor.setNome(fornecedorDto.getNome());
        fornecedor.setSenha(fornecedorDto.getSenha());
        return FornecedorMapper.toDto(repository.save(fornecedor));
    }


    @Override
    public void deletarFornecedor(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Fornecedor searchFornecedorById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));
    }
}
