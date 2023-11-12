package com.midgard.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access-token")
    private String accessToken;
    @JsonProperty("refresh-token")
    private String refreshToken;
}
