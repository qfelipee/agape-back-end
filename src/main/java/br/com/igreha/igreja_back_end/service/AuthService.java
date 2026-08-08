package br.com.igreha.igreja_back_end.service;

import br.com.igreha.igreja_back_end.dto.user.LoginRequestDTO;
import br.com.igreha.igreja_back_end.dto.user.LoginResponseDTO;
import br.com.igreha.igreja_back_end.entity.user.User;
import br.com.igreha.igreja_back_end.exception.UserNotFoundException;
import br.com.igreha.igreja_back_end.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final br.com.igreha.igreja_back_end.repository.user.UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        // Se email/senha estiverem errados, isso já lança exceção automaticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(
                token,
                user.getNome(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}