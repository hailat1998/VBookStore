package com.hd.app.vbookstore;

import com.hd.vbookstore.data.UserRepository;
import com.hd.vbookstore.domain.enums.Role;
import com.hd.vbookstore.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initializeAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(Role.ROLE_ADMIN);
                adminRoles.add(Role.ROLE_USER);

                User adminUser = new User(
                        "admin",
                        passwordEncoder.encode("1998@ht1234"),
                        "Admin User",
                        "Admin Street",
                        "Admin City",
                        "AS",
                        "12345",
                        "1234567890",
                        "admin@example.com"
                );
                 adminUser.getRoles().addAll(adminRoles);

                userRepository.save(adminUser);

                System.out.println("Admin user has been initialized!");
            }
        };
    }
}