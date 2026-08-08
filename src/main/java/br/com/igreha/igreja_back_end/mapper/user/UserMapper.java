package br.com.igreha.igreja_back_end.mapper.user;

import br.com.igreha.igreja_back_end.dto.user.UserResponseDTO;
import br.com.igreha.igreja_back_end.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getFuncao(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}