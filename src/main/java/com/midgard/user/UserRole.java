package com.midgard.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum UserRole {
    USER(
            Set.of(
                    UserPermission.USER_READ,
                    UserPermission.USER_CREATE,
                    UserPermission.USER_UPDATE,
                    UserPermission.USER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    UserPermission.ADMIN
            )
    ),
    MANAGER(
            Set.of(
                    UserPermission.MANAGER_READ,
                    UserPermission.MANAGER_CREATE,
                    UserPermission.MANAGER_UPDATE,
                    UserPermission.MANAGER_DELETE
            )
    );

    @Getter
    private final Set<UserPermission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getUserPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
