package com.filmsreviews.filmsreviews_api.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.filmsreviews.filmsreviews_api.entities.Role;
import com.filmsreviews.filmsreviews_api.entities.User;
import com.filmsreviews.filmsreviews_api.repository.RoleRepository;
import com.filmsreviews.filmsreviews_api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        var roleAdmin = roleRepository.findById(Role.Values.ADMIN.getRoleId()).orElse(null);
        
        var userAdmin = userRepository.findByUsername("admin");
        
        userAdmin.ifPresentOrElse(
            (user) -> {
                System.out.println("User admin already exists");
            },
            () -> {
                var user = new User();
                user.setUsername("admin");
                user.setPassword(bCryptPasswordEncoder.encode("123"));
                user.setRoles(Set.of(roleAdmin));
                userRepository.save(user);
                System.out.println("User admin created");
            }
        );
    }

}