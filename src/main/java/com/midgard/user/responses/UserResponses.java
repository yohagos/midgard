package com.midgard.user.responses;

import com.midgard.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponses {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private UserRole role;
}
