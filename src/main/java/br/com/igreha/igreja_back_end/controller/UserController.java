package br.com.igreha.igreja_back_end.controller;

import br.com.igreha.igreja_back_end.dto.user.UserRegisterRequestDTO;
import br.com.igreha.igreja_back_end.dto.user.UserResponseDTO;
import br.com.igreha.igreja_back_end.dto.user.UserUpdateRequestDTO;
import br.com.igreha.igreja_back_end.entity.user.User;
import br.com.igreha.igreja_back_end.mapper.user.UserMapper;
import br.com.igreha.igreja_back_end.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterRequestDTO request) {
        UserResponseDTO response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdateRequestDTO request,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        UserResponseDTO response = userService.update(id, request, usuarioLogado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        userService.delete(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(
            @PathVariable Integer id,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        return ResponseEntity.ok(userService.findById(id, usuarioLogado));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@AuthenticationPrincipal User usuarioLogado) {
        return ResponseEntity.ok(userMapper.toResponseDTO(usuarioLogado));
    }
}