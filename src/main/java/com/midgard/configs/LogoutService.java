package com.midgard.configs;

import com.midgard.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        jwt = authHeader.substring(7);
        var storesToken = tokenRepository
                .findByToken(jwt)
                .orElse(null);
        if (storesToken != null) {
            storesToken.setExpired(true);
            storesToken.setRevoked(true);
            tokenRepository.save(storesToken);
            SecurityContextHolder.clearContext();
        }
    }
}
