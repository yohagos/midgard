package com.midgard.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @GetMapping("")
    public List<UserEntity> getAllUsers() {
        // OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // log.info("userController", user);
        return userService.getAllUsers();
    }

    @GetMapping("/")
    public UserEntity getUserByEmail(
            @RequestParam("email") String email
    ) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/name")
    public List<UserEntity> getUserByName(
            @RequestParam("name") String name
    ) {
        return userService.findUsersByName(name);
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
        /*UserEntity entity = new UserEntity(firstname, lastname, email, password);
        userService.saveUser(entity);*/
        return "saved";
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
