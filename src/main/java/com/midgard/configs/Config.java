package com.midgard.configs;

import com.midgard.auth.AuthenticationService;
import com.midgard.auth.RegisterRequest;
import com.midgard.comment.CommentEntity;
import com.midgard.comment.CommentRepository;
import com.midgard.ticket.TicketCategories;
import com.midgard.ticket.TicketEntity;
import com.midgard.ticket.TicketRepository;
import com.midgard.ticket.TicketStatus;
import com.midgard.user.UserEntity;
import com.midgard.user.UserRepository;
import com.midgard.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final PasswordEncoder passwordEncoder;


    @Bean
    CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("admin")
                    .lastname("admin")
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            System.out.println("Admin " + admin.getEmail() + ", Token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .firstname("James")
                    .lastname("King")
                    .email("james@king.com")
                    .password(passwordEncoder.encode("test"))
                    .build();
            System.out.println("Manager " + manager.getEmail() + ", Token: " + service.register(manager).getAccessToken());
        };
    }
}