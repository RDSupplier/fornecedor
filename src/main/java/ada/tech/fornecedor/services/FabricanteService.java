package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.FabricanteDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Fabricante;
import ada.tech.fornecedor.domain.entities.Produto;
import ada.tech.fornecedor.domain.mappers.FabricanteMapper;
import ada.tech.fornecedor.repositories.IFabricanteRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FabricanteService implements IFabricanteService {
    private final IFabricanteRepository repository;

    public FabricanteDto criarFabricante(FabricanteDto fabricanteDto) {
        Fabricante fabricante = FabricanteMapper.toEntity(fabricanteDto);
        repository.save(fabricante);
        return FabricanteMapper.toDto(fabricante);
    }

    @Override
    public List<FabricanteDto> listarFabricantes() throws NotFoundException {
        return repository.findAll().stream().map(FabricanteMapper::toDto).toList();
    }

    @Override
    public FabricanteDto listarFabricante(int id) throws NotFoundException {
        return FabricanteMapper.toDto(searchFabricanteById(id));
    }

    @Override
    public FabricanteDto atualizarFabricante(int id, FabricanteDto fabricanteDto) throws NotFoundException {
        final Fabricante fabricante = searchFabricanteById(id);

        fabricante.setNome(fabricanteDto.getNome());
        fabricante.setCnpj(fabricanteDto.getCnpj());

        repository.save(fabricante);

        return FabricanteMapper.toDto(fabricante);
    }

    @Override
    public void deletarFabricante(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Fabricante searchFabricanteById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Fabricante.class, String.valueOf(id)));
    }
}
