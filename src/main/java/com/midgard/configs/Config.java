package com.midgard.configs;

import com.midgard.comment.CommentEntity;
import com.midgard.comment.CommentRepository;
import com.midgard.ticket.TicketEntity;
import com.midgard.ticket.TicketRepository;
import com.midgard.ticket.TicketStatus;
import com.midgard.user.UserEntity;
import com.midgard.user.UserRepository;
import com.midgard.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            TicketRepository ticketRepository,
            CommentRepository commentRepository) {
        return args -> {
            userRepository.saveAll(
                    List.of(
                            new UserEntity(
                                    "James",
                                    "King",
                                    "james@king.com",
                                    UserRole.USER_READ,
                                    "test"),
                            new UserEntity(
                                    "Jennifer",
                                    "Queen",
                                    "jen@queen.com",
                                    UserRole.USER_READ,
                                    "test"),
                            new UserEntity(
                                    "Micheal",
                                    "Jordan",
                                    "michael@jordan.com",
                                    UserRole.USER_READ,
                                    "test"),
                            new UserEntity(
                                    "Asuka",
                                    "Fuji",
                                    "asuka@fuji.com",
                                    UserRole.USER_DELETE,
                                    "test"),
                            new UserEntity(
                                    "Admin",
                                    "Admin",
                                    "admin",
                                    UserRole.USER_CREATE,
                                    "admin"
                            )
                    )
            );

            var ticketOwner = userRepository.findById(1L);
            var otherUser = userRepository.findUserById(3L);
            var includedUser = userRepository.findById(2L);

            var ticket = new TicketEntity(
                    "database migration",
                    ticketOwner.get(),
                    List.of(
                            includedUser.get()
                    ),
                    "content",
                    TicketStatus.OPEN
            );

            ticketRepository.saveAll(
                    List.of(
                            ticket,
                            new TicketEntity(
                                    "security configuration",
                                    includedUser.get(),
                                    List.of(
                                            ticketOwner.get(),
                                            otherUser.get()
                                    ),
                                    "content",
                                    TicketStatus.OPEN
                            ),
                            new TicketEntity(
                                    "security enhancement",
                                    includedUser.get(),
                                    List.of(
                                            ticketOwner.get()
                                    ),
                                    "content",
                                    TicketStatus.OPEN
                            )
                    )
            );

            commentRepository.saveAll(
                    List.of(
                            new CommentEntity(
                                    ticket,
                                    ticketOwner.get(),
                                    "need to be fixed"
                            ),
                            new CommentEntity(
                                    ticket,
                                    includedUser.get(),
                                    "fixed"
                            )
                    )
            );

            userRepository.findAll().forEach(System.out::println);
            ticketRepository.findAll().forEach(System.out::println);
            commentRepository.findAll().forEach(System.out::println);
        };
    }
}