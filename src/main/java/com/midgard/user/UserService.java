package com.midgard.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        userRepository = repository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent())
            return optionalUser;
        throw new IllegalArgumentException("User by id " + id + " not found");
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent())
            throw new IllegalArgumentException("User by id " + id + " not found");

        userRepository.deleteById(id);
    }
}
