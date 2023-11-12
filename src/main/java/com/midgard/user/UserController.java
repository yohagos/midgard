package com.midgard.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @GetMapping("")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/")
    public UserEntity getUserByEmail(
            @RequestParam("email") String email
    ) {
        return userService.findUserByEmail(email);
    }

    @GetMapping(params = "name")
    public List<UserEntity> getUserByName(
            @RequestParam("name") String name
    ) {
        return userService.findUsersByName(name);
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
