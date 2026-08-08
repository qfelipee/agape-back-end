package br.com.igreha.igreja_back_end.service.user;

import br.com.igreha.igreja_back_end.dto.user.UserRegisterRequestDTO;
import br.com.igreha.igreja_back_end.dto.user.UserResponseDTO;
import br.com.igreha.igreja_back_end.dto.user.UserUpdateRequestDTO;
import br.com.igreha.igreja_back_end.entity.user.User;
import br.com.igreha.igreja_back_end.exception.AccessDeniedCustomException;
import br.com.igreha.igreja_back_end.exception.EmailAlreadyExistsException;
import br.com.igreha.igreja_back_end.exception.UserNotFoundException;
import br.com.igreha.igreja_back_end.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final br.com.igreha.igreja_back_end.repository.user.UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDTO register(UserRegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Já existe um usuário com esse email");
        }

        User user = new User();
        user.setNome(request.getNome());
        user.setFuncao(request.getFuncao());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha())); // <- hash acontece aqui
        user.setRole(request.getRole());

        User salvo = userRepository.save(user);

        return userMapper.toResponseDTO(salvo);
    }

    public UserResponseDTO update(Integer id, UserUpdateRequestDTO request, User usuarioLogado) {
        // Só o próprio usuário (ou um ADMIN) pode atualizar
        boolean isDono = usuarioLogado.getId().equals(id);
        boolean isAdmin = usuarioLogado.getRole().name().equals("PASTOR");

        if (!isDono && !isAdmin) {
            throw new AccessDeniedCustomException("Você só pode atualizar seu próprio perfil");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Se o email mudou, confere que não existe outro usuário com esse email
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Já existe um usuário com esse email");
        }

        user.setNome(request.getNome());
        user.setFuncao(request.getFuncao());
        user.setEmail(request.getEmail());

        // Só atualiza a senha se um valor novo foi enviado
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            user.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        User atualizado = userRepository.save(user);
        return userMapper.toResponseDTO(atualizado);
    }

    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        userRepository.delete(user);
    }
    public List<UserResponseDTO> listAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO findById(Integer id, User usuarioLogado) {
        boolean isDono = usuarioLogado.getId().equals(id);
        boolean isAdmin = usuarioLogado.getRole().name().equals("PASTOR");

        if (!isDono && !isAdmin) {
            throw new AccessDeniedCustomException("Você só pode visualizar seu próprio perfil");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        return userMapper.toResponseDTO(user);
    }
}