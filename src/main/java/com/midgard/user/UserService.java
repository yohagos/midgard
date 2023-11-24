package com.midgard.user;


import com.midgard.configs.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

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

    private String getCurrentUsername() {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var header = request.getHeader("Authorization").split(" ")[1];
        return jwtService.extractUsername(header);
    }


    public UserPermissionResponse updateUserPermission(UserPermissionRequest request) {
        var username = getCurrentUsername();
        var optionalUser = userRepository.findUserById(request.getUser_id());
        var optionalCurrentUser = userRepository.findUserByEmail(username);
        if (!optionalUser.isPresent() || !optionalCurrentUser.isPresent())
            throw new IllegalStateException("cannot find user with id " + request.getUser_id());
        var user = optionalUser.get();
        var currentUser = optionalCurrentUser.get();
        log.info(currentUser.getRole().toString());
        if (currentUser.getRole().equals(UserRole.USER))
            throw new IllegalStateException("Not enough permissions to update user permissions for " + username);
        switch (request.getNewPermission()) {
            case "ADMIN":
                user.setRole(UserRole.ADMIN);
                break;
            case "MANAGER":
                user.setRole(UserRole.MANAGER);
                break;
            default:
                user.setRole(UserRole.USER);
        }
        userRepository.save(user);
        return new UserPermissionResponse(
                request.getNewPermission(),
                username,
                true
        );
    }


}
