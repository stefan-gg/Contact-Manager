package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.user.PostUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import com.ing.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUserMapper {

    private final UserRepository userRepository;

    public User convertPostUserDTOToUser(PostUserDTO postUserDTO){
        User user = new User();

        user.setEmail(postUserDTO.getEmail());
        user.setPassword(postUserDTO.getPassword());
        user.setFirstName(postUserDTO.getFirstName());
        user.setLastName(postUserDTO.getLastName());
        user.setRole(Role.valueOf(postUserDTO.getRole()));

        return user;
    }
}
