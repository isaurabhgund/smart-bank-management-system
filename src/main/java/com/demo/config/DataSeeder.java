package com.demo.config;

import com.demo.model.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Define the default admin credentials
        String adminEmail = "admin@smartbank.com";

        // Check if admin already exists to prevent duplicate entries
        Optional<User> adminOptional = userRepository.findByEmail(adminEmail);

        if (adminOptional.isEmpty()) {
            User admin = new User();
            admin.setName("System Administrator");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123")); // Securely hashed
            admin.setRole("ADMIN");
            admin.setEnabled(true);

            userRepository.save(admin);
            System.out.println("✅ DEFAULT ADMIN USER CREATED: " + adminEmail + " / admin123");
        } else {
            System.out.println("ℹ️ ADMIN USER ALREADY EXISTS.");
        }
    }
}
