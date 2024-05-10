package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.mappers.FornecedorMapper;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService implements IFornecedorService {

    private final IFornecedorRepository repository;

    @Override
    public FornecedorDto criarFornecedor(FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);
        return FornecedorMapper.toDto(repository.save(fornecedor));
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
