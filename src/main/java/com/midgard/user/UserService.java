package com.midgard.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findUserById(id);

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

    public UserEntity findUserByEmail(String email) {
        var optionalUser = userRepository.findUserByEmail(email);
        System.out.println(optionalUser);
        if (!optionalUser.isPresent())
            throw new IllegalArgumentException("No user has the email " + email);
        return optionalUser.get();
    }

    public List<UserEntity> findUsersByName(String name) {
        var optionalUsers = userRepository.findUsersByLastname(name);
        System.out.println(optionalUsers);
        if (!optionalUsers.isPresent())
            throw new IllegalArgumentException("No users by the name of "+ name);
        return optionalUsers.get();
    }

    public void changePassword(
            ChangePasswordRequest request,
            Principal connectedUser
    ) {
        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
            throw new IllegalStateException("wrong password");
        if (!request.getNewPassword().equals(request.getConfirmationPassword()))
            throw new IllegalStateException("Password are not the same");

        user.setPassword(passwordEncoder.encode((request.getNewPassword())));
        userRepository.save(user);
    }
}
