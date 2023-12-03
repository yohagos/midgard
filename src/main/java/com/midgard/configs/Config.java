package com.midgard.configs;

import com.midgard.auth.AuthenticationService;
import com.midgard.auth.RegisterRequest;
import com.midgard.comment.CommentEntity;
import com.midgard.comment.CommentRepository;
import com.midgard.ticket.*;
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
            AuthenticationService service,
            TicketRepository ticketRepository,
            UserRepository userRepository
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

            var user1 = RegisterRequest.builder()
                    .firstname("Jennifer")
                    .lastname("King")
                    .email("jennifer@king.com")
                    .password(passwordEncoder.encode("test"))
                    .build();
            System.out.println("Admin " + user1.getEmail() + ", Token: " + service.register(user1).getAccessToken());

            var user2 = RegisterRequest.builder()
                    .firstname("Max")
                    .lastname("Stein")
                    .email("max@stein.com")
                    .password(passwordEncoder.encode("test"))
                    .build();
            System.out.println("Manager " + user2.getEmail() + ", Token: " + service.register(user2).getAccessToken());

            var adminEntity = userRepository.findUserByEmail(admin.getEmail()).orElseThrow();
            var managerEntity = userRepository.findUserByEmail(manager.getEmail()).orElseThrow();
            var userEntity = userRepository.findUserByEmail(user1.getEmail()).orElseThrow();
            var user2Entity = userRepository.findUserByEmail(user2.getEmail()).orElseThrow();

            var ticketDatabaseIntegration = new TicketEntity();
            ticketDatabaseIntegration.setTitle("Database integration");
            ticketDatabaseIntegration.setOwner(managerEntity);
            ticketDatabaseIntegration.setIncludedUsers(List.of(userEntity));
            ticketDatabaseIntegration.setContent("Database needs to be ....");
            ticketDatabaseIntegration.setStatus(TicketStatus.OPEN);
            ticketDatabaseIntegration.setCategories(List.of(TicketCategories.DATABASE));

            var ticketDatabaseConfiguration = new TicketEntity();
            ticketDatabaseConfiguration.setTitle("Database configuration");
            ticketDatabaseConfiguration.setOwner(adminEntity);
            ticketDatabaseConfiguration.setIncludedUsers(List.of(user2Entity));
            ticketDatabaseConfiguration.setContent("Database configuration needs to be ....");
            ticketDatabaseConfiguration.setStatus(TicketStatus.IMPLEMENTING);
            ticketDatabaseConfiguration.setCategories(List.of(TicketCategories.DATABASE, TicketCategories.SECURITY));


            var ticketFirewallUpgrade = new TicketEntity();
            ticketFirewallUpgrade.setTitle("Firewall upgrade");
            ticketFirewallUpgrade.setOwner(adminEntity);
            ticketFirewallUpgrade.setIncludedUsers(List.of(userEntity));
            ticketFirewallUpgrade.setContent("Firewall needs to be ....");
            ticketFirewallUpgrade.setStatus(TicketStatus.OPEN);
            ticketFirewallUpgrade.setCategories(List.of(TicketCategories.DATABASE));


            var ticketAngularUpgrade = new TicketEntity();
            ticketAngularUpgrade.setTitle("Angular upgrade");
            ticketAngularUpgrade.setOwner(adminEntity);
            ticketAngularUpgrade.setIncludedUsers(List.of(user2Entity));
            ticketAngularUpgrade.setContent("Database configuration needs to be ....");
            ticketAngularUpgrade.setStatus(TicketStatus.IMPLEMENTING);
            ticketAngularUpgrade.setCategories(List.of(TicketCategories.DATABASE, TicketCategories.SECURITY));

            ticketRepository.saveAll(
                    List.of(
                            ticketDatabaseIntegration,
                            ticketDatabaseConfiguration,
                            ticketFirewallUpgrade,
                            ticketAngularUpgrade
                    )
            );

            var ticketDBINT = ticketRepository.findById(2L).orElseThrow();
            ticketDBINT.setPriority(TicketPriority.SEMI_HIGH);
            ticketRepository.save(ticketDBINT);
        };
    }
}