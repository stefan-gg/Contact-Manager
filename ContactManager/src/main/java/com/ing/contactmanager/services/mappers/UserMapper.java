package com.ing.contactmanager.services.mappers;

import com.ing.contactmanager.dtos.request.user.RequestUserDTO;
import com.ing.contactmanager.dtos.response.user.ResponseUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public List<ResponseUserDTO> getAllUsers(List<User> users) {

        return (users
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList()));
    }

    public ResponseUserDTO convertToUserDTO(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();

        responseUserDTO.setUuid(user.getUid());
        responseUserDTO.setEmail(user.getEmail());
        responseUserDTO.setFirstName((user.getFirstName()));
        responseUserDTO.setLastName(user.getLastName());
        responseUserDTO.setCreatedAt(user.getCreatedAt());
        responseUserDTO.setUpdatedAt(user.getUpdatedAt());
        responseUserDTO.setRole(user.getRole().toString());

        return responseUserDTO;
    }

    public User convertPostUserDTOToUser(RequestUserDTO requestUserDTO) {
        User user = new User();

        user.setEmail(requestUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));
        user.setFirstName(requestUserDTO.getFirstName());
        user.setLastName(requestUserDTO.getLastName());
        user.setRole(Role.valueOf(requestUserDTO.getRole()));

        return user;
    }
}
