package com.midgard.configs;

import com.midgard.user.UserEntity;
import com.midgard.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            repository.saveAll(
                    List.of(
                            new UserEntity("James", "King", "james@king.com", "test"),
                            new UserEntity("Jennifer", "Queen", "jen@queen.com", "test")
                    )
            );

            repository.findAll().forEach(System.out::println);
        };
    }
}
