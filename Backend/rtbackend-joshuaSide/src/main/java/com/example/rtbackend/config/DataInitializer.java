package com.example.rtbackend.config;

import com.example.rtbackend.domain.entities.Role;
import com.example.rtbackend.repo.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepo roleRepo) {
        return args -> {
            System.out.println("=================================");
            System.out.println("🔄 DataInitializer Starting...");
            System.out.println("Current roles count: " + roleRepo.count());
            
            // Check if roles already exist
            if (roleRepo.count() == 0) {
                System.out.println("📝 Creating default roles...");
                
                roleRepo.save(new Role(null, "MANAGER"));  // ← All uppercase
                roleRepo.save(new Role(null, "CASHIER"));  // ← All uppercase
                roleRepo.save(new Role(null, "WAITER"));   // ← All uppercase
                roleRepo.save(new Role(null, "CHEF"));     // ← All uppercase
                
                System.out.println("✅ Default roles initialized: MANAGER, CASHIER, WAITER, CHEF");
            } else {
                System.out.println("ℹ️ Roles already exist in database");
                roleRepo.findAll().forEach(role -> 
                    System.out.println("   - " + role.getRoleName())
                );
            }
            System.out.println("=================================");
        };
    }
}