package com.midgard.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER_READ("USER:READ"),
    USER_CREATE("USER:CREATE"),
    USER_UPDATE("USER:UPDATE"),
    USER_DELETE("USER:DELETE"),
    MANAGER_CREATE("USER:CREATE"),
    MANAGER_READ("MANAGER:READ"),
    MANAGER_UPDATE("MANAGER:UPDATE"),
    MANAGER_DELETE("MANAGER:DELETE"),
    ADMIN("ADMIN");

    @Getter
    private final String permissions;

}
