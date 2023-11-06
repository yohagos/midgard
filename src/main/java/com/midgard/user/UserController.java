package com.midgard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        userService = service;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        OAuth2User user = (
                (OAuth2User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal());
        return userService.getAllUsers();
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String passwordRepeat
    ) {
        if (!password.equals(passwordRepeat))
            throw new IllegalArgumentException("Passwords do not match");
        UserEntity entity = new UserEntity(firstname, lastname, email, password);
        userService.saveUser(entity);
        return "saved";
    }
}
