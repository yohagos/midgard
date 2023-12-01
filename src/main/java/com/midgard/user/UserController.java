package com.midgard.user;

import com.midgard.token.Token;
import com.midgard.token.TokenRepository;
import com.midgard.user.responses.UserResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final TokenRepository tokenRepository;

    @GetMapping("")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponses> getUserByEmail(
            @PathVariable(value = "email") String request
    ) {
        return ResponseEntity.ok(userService.findUserByEmail(request));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(
            @PathVariable(value = "id") Long id
    ) {
        userService.deleteUser(id);
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/permission")
    public ResponseEntity<UserPermissionResponse> updateUserPermissions(
            @RequestBody UserPermissionRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserPermission(request));
    }

    @GetMapping("/token")
    public List<Token> getTokens() {
        var tokens = tokenRepository.findAll();
        return tokens;
    }
}
