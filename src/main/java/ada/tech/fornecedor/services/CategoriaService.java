package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.CategoriaDto;
import ada.tech.fornecedor.domain.dto.exceptions.AlreadyExistsException;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Categoria;
import ada.tech.fornecedor.domain.mappers.CategoriaMapper;
import ada.tech.fornecedor.repositories.ICategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {
    private final ICategoriaRepository repository;

    @Override
    @Transactional
    public CategoriaDto criarCategoria(CategoriaDto categoriaDto) {
        String nomeCategoria = categoriaDto.getCategoria();
        Categoria categoriaExiste = repository.findByCategoria(nomeCategoria);

        Categoria categoria = CategoriaMapper.toEntity(categoriaDto);

        if(categoriaExiste != null) {
            throw new AlreadyExistsException(Categoria.class, String.valueOf(categoriaExiste.getId()));
        }

        repository.save(categoria);

        return CategoriaMapper.toDto(categoria);
    }

    @Override
    public List<CategoriaDto> listarCategorias() throws NotFoundException {
        return repository.findAll().stream().map(CategoriaMapper::toDto).toList();
    }

    @Override
    public CategoriaDto listarCategoria(int id) throws NotFoundException {
        return CategoriaMapper.toDto(searchCategoriaById(id));
    }

    @Override
    public CategoriaDto atualizarCategoria(int id, CategoriaDto categoriaDto) throws NotFoundException {
        final Categoria categoria = searchCategoriaById(id);

        categoria.setCategoria(categoriaDto.getCategoria());

        repository.save(categoria);

        return CategoriaMapper.toDto(categoria);
    }

    @Override
    public void deletarCategoria(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Categoria searchCategoriaById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Categoria.class, String.valueOf(id)));
    }
}
