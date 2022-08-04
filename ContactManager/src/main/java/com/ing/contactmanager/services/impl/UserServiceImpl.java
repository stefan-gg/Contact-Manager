package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.entities.User;
import com.ing.contactmanager.repositories.UserRepository;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CRUDService<User> {

    private final UserRepository userRepository;

    @Override
    public void deleteByUuid(UUID uuid) {
        userRepository.deleteByUid(uuid);
    }

    @Override
    public User getByUuid(UUID uuid) {
        return userRepository.findByUid(uuid);//.orElseThrow(() -> new NoSuchElementException("UserService.notFound"));
    }

    @Override
    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllByOrderByLastNameAsc();
    }
}
