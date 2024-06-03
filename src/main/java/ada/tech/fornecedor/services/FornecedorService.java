package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EnderecoDto;
import ada.tech.fornecedor.domain.dto.FornecedorDto;
import ada.tech.fornecedor.domain.dto.LoginDto;
import ada.tech.fornecedor.domain.dto.LoginResponse;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.Endereco;
import ada.tech.fornecedor.domain.entities.Fornecedor;
import ada.tech.fornecedor.domain.entities.Role;
import ada.tech.fornecedor.domain.mappers.EnderecoMapper;
import ada.tech.fornecedor.domain.mappers.FornecedorMapper;
import ada.tech.fornecedor.repositories.IEnderecoRepository;
import ada.tech.fornecedor.repositories.IFornecedorRepository;
import ada.tech.fornecedor.repositories.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorService implements IFornecedorService {

    private final IFornecedorRepository repository;
    private final IEnderecoRepository enderecoRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    private Role criarRoleSeNaoExistir(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Override
    @Transactional
    public FornecedorDto criarFornecedor(FornecedorDto fornecedorDto) {
        Endereco endereco = verificarEndereco(fornecedorDto.getEndereco());
        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);
        fornecedor.setEnderecos(endereco);
        Role roleFornecedor = criarRoleSeNaoExistir("ROLE_FORNECEDOR");
        String senhaEncoded = passwordEncoder.encode(fornecedorDto.getSenha());
        fornecedor.setRole(roleFornecedor);
        fornecedor.setSenha(senhaEncoded);
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
    @Transactional
    public FornecedorDto atualizarFornecedor(int id, FornecedorDto fornecedorDto) throws NotFoundException {
        Fornecedor fornecedor = FornecedorMapper.toEntity(fornecedorDto);

        Optional<Endereco> enderecoExistente = enderecoRepository.findById(fornecedorDto.getEndereco().getId());

        Endereco endereco;
        if(enderecoExistente.isPresent()) {
            EnderecoDto enderecoDto = fornecedorDto.getEndereco();
            endereco = enderecoExistente.get();
            endereco.setRua(enderecoDto.getRua());
            endereco.setNumero(enderecoDto.getNumero());
            endereco.setComplemento(enderecoDto.getComplemento());
            endereco.setBairro(enderecoDto.getBairro());
            endereco.setCidade(enderecoDto.getCidade());
            endereco.setEstado(enderecoDto.getEstado());
            endereco.setCep(enderecoDto.getCep());
        } else {
            throw new NotFoundException(Endereco.class, String.valueOf(enderecoExistente.get().getId()));
        }

        Fornecedor fornecedorExistente = repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));

        fornecedorExistente.setCnpj(fornecedor.getCnpj());
        fornecedorExistente.setNire(fornecedor.getNire());
        fornecedorExistente.setNome(fornecedor.getNome());
        fornecedorExistente.setSenha(fornecedor.getSenha());

        fornecedorExistente.setEnderecos(endereco);

        fornecedorExistente = repository.save(fornecedorExistente);

        return FornecedorMapper.toDto(fornecedorExistente);
    }

    @Override
    public void deletarFornecedor(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Fornecedor searchFornecedorById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Fornecedor.class, String.valueOf(id)));
    }

    private Endereco verificarEndereco(EnderecoDto enderecoDto) {
        Endereco endereco = EnderecoMapper.toEntity(enderecoDto);

        Optional<Endereco> enderecoExistente = enderecoRepository.findByEnderecoAttributes(endereco);

        if (enderecoExistente.isPresent()) {
            return enderecoExistente.get();
        } else {
            return enderecoRepository.save(endereco);
        }
    }

    public LoginResponse loginFornecedor(LoginDto loginDto) {
        long cnpj = loginDto.getCnpj();
        String senha = loginDto.getSenha();
        Fornecedor fornecedor = repository.findByCnpj(cnpj);
        if (fornecedor != null) {
            String senhaEncoded = fornecedor.getSenha();
            if (passwordEncoder.matches(senha, senhaEncoded)) {
                return new LoginResponse("Login bem-sucedido", true);
            } else {
                return new LoginResponse("Dados incorretos", false);
            }
        } else {
            return new LoginResponse("Fornecedor não encontrado", false);
        }
    }



}
