package com.midgard.user;

import com.midgard.token.Token;
import com.midgard.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final TokenRepository tokenRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('user:read')")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(params = "name")
    @PreAuthorize("hasAuthority('user:read')")
    public List<UserEntity> getUserByName(
            @RequestParam("name") String name
    ) {
        return userService.findUsersByName(name);
    }

    @GetMapping(params = "email")
    @PreAuthorize("hasAuthority('user:read')")
    public UserEntity getUserByEmail(
            @RequestParam("email") String email
    ) {
        return userService.findUserByEmail(email);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public void deleteUserById(
            @PathVariable Long id
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
    @PreAuthorize("hasAuthority('user:put')")
    public ResponseEntity<UserPermissionResponse> updateUserPermissions(
            @RequestBody UserPermissionRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserPermission(request));
    }

    @GetMapping("/token")
    @PreAuthorize("hasAuthority('user:read')")
    public List<Token> getTokens() {
        var tokens = tokenRepository.findAll();
        log.info(tokens.toString());
        return tokens;
    }
}
