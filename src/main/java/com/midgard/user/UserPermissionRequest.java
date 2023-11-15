package com.midgard.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionRequest {

    private String currentPermission;
    private String newPermission;
    private Long user_id;
}
