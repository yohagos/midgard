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

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final PasswordEncoder passwordEncoder;


    @Bean
    CommandLineRunner commandLineRunner(
            AuthenticationService service,
            TicketRepository ticketRepository,
            UserRepository userRepository,
            CommentRepository commentRepository
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
            ticketDatabaseIntegration.setCreatedAt(LocalDateTime.of(2023, 11, 28, 10, 15));
            ticketDatabaseIntegration.setDeadline(LocalDateTime.of(2023, 12, 20, 10, 15));
            ticketDatabaseIntegration.setPriority(TicketPriority.LOW);

            var ticketDatabaseConfiguration = new TicketEntity();
            ticketDatabaseConfiguration.setTitle("Database configuration");
            ticketDatabaseConfiguration.setOwner(adminEntity);
            ticketDatabaseConfiguration.setIncludedUsers(List.of(user2Entity));
            ticketDatabaseConfiguration.setContent("Database configuration needs to be ....");
            ticketDatabaseConfiguration.setStatus(TicketStatus.IMPLEMENTING);
            ticketDatabaseConfiguration.setCategories(List.of(TicketCategories.DATABASE, TicketCategories.SECURITY));
            ticketDatabaseConfiguration.setCreatedAt(LocalDateTime.of(2023, 11, 27, 10, 15));
            ticketDatabaseConfiguration.setDeadline(LocalDateTime.of(2023, 12, 23, 10, 15));
            ticketDatabaseConfiguration.setPriority(TicketPriority.SEMI_HIGH);

            var ticketFirewallUpgrade = new TicketEntity();
            ticketFirewallUpgrade.setTitle("Firewall upgrade");
            ticketFirewallUpgrade.setOwner(adminEntity);
            ticketFirewallUpgrade.setIncludedUsers(List.of(userEntity));
            ticketFirewallUpgrade.setContent("Firewall needs to be ....");
            ticketFirewallUpgrade.setStatus(TicketStatus.OPEN);
            ticketFirewallUpgrade.setCategories(List.of(TicketCategories.DATABASE));
            ticketFirewallUpgrade.setCreatedAt(LocalDateTime.of(2023, 11, 22, 11, 15));
            ticketFirewallUpgrade.setDeadline(LocalDateTime.of(2023, 12, 21, 10, 15));
            ticketFirewallUpgrade.setPriority(TicketPriority.HIGH);

            var ticketAngularUpgrade = new TicketEntity();
            ticketAngularUpgrade.setTitle("Angular upgrade");
            ticketAngularUpgrade.setOwner(adminEntity);
            ticketAngularUpgrade.setIncludedUsers(List.of(user2Entity));
            ticketAngularUpgrade.setContent("Database configuration needs to be ....");
            ticketAngularUpgrade.setStatus(TicketStatus.IMPLEMENTING);
            ticketAngularUpgrade.setCategories(List.of(TicketCategories.DATABASE, TicketCategories.SECURITY));
            ticketAngularUpgrade.setCreatedAt(LocalDateTime.of(2023, 11, 28, 12, 15));
            ticketFirewallUpgrade.setDeadline(LocalDateTime.of(2023, 12, 22, 10, 15));
            ticketAngularUpgrade.setPriority(TicketPriority.SEMI_LOW);

            ticketRepository.saveAll(
                    List.of(
                            ticketDatabaseIntegration,
                            ticketDatabaseConfiguration,
                            ticketFirewallUpgrade,
                            ticketAngularUpgrade
                    )
            );

            var ticketDBINT = ticketRepository.findById(2L).orElseThrow();
            ticketRepository.save(ticketDBINT);

            var commentOne = new CommentEntity();
            commentOne.setTicket(ticketDBINT);
            commentOne.setUser(managerEntity);
            commentOne.setContent("How much more time do you need?");
            commentOne.setTimestamp(LocalDateTime.now());

            var commentTwo = new CommentEntity();
            commentTwo.setTicket(ticketDBINT);
            commentTwo.setUser(userEntity);
            commentTwo.setContent("Do you make progress?");
            commentTwo.setTimestamp(LocalDateTime.now());

            var commentThree = new CommentEntity();
            commentThree.setTicket(ticketDBINT);
            commentThree.setUser(userEntity);
            commentThree.setContent("I need some help");
            commentThree.setTimestamp(LocalDateTime.now());

            var commentFour = new CommentEntity();
            commentFour.setTicket(ticketDBINT);
            commentFour.setUser(managerEntity);
            commentFour.setContent("How can I help you?");
            commentFour.setTimestamp(LocalDateTime.now());

            commentRepository.saveAll(
                    List.of(
                            commentOne, commentTwo, commentThree, commentFour
                    )
            );

        };
    }
}