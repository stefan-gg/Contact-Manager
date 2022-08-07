package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.user.PostUserDTO;
import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.entities.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class PostUserMapper {

    public User convertPostUserDTOToUser(PostUserDTO postUserDTO){
        User user = new User();

        user.setUid(postUserDTO.getUuid());
        user.setEmail(postUserDTO.getEmail());
        user.setPassword(postUserDTO.getPassword());
        user.setFirstName(postUserDTO.getFirstName());
        user.setLastName(postUserDTO.getLastName());
        user.setRole(Role.valueOf(postUserDTO.getRole()));

        return user;
    }

    public PostUserDTO covertUserToPostUserDTO(User user){
        PostUserDTO postUserDTO = new PostUserDTO();

        postUserDTO.setId(user.getId());
        postUserDTO.setUuid(user.getUid());
        postUserDTO.setEmail(user.getEmail());
        postUserDTO.setPassword(user.getPassword());
        postUserDTO.setRole(user.getRole().toString());
        postUserDTO.setFirstName(user.getFirstName());
        postUserDTO.setLastName(user.getLastName());
        postUserDTO.setEmail(user.getEmail());

        return postUserDTO;
    }
}
