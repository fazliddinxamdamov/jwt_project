package pdp.uz.jwtproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pdp.uz.jwtproject.domain.Role;
import pdp.uz.jwtproject.domain.User;
import pdp.uz.jwtproject.service.UserService;

import java.util.ArrayList;

@EnableScheduling
@SpringBootApplication
public class JwtProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtProjectApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CommandLineRunner run(UserService userService) {
//        return args -> {
//            // Create Roles
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_USER"));
//
//            // Create Users
//            userService.saveUser(new User(null, "Super Admin", "super-admin", "12345", "superadmin@gmail.com", "super-admin", true, new ArrayList<>()));
//            userService.saveUser(new User(null, "Admin", "admin", "12345", "admin@gmail.com", "admin", true, new ArrayList<>()));
//            userService.saveUser(new User(null, "User", "user", "12345", "user@gmail.com", "user", true, new ArrayList<>()));
//
//            // Attach Role to User
//            userService.attachRoleToUser("super-admin", "ROLE_SUPER_ADMIN");
//            userService.attachRoleToUser("admin", "ROLE_ADMIN");
//            userService.attachRoleToUser("user", "ROLE_USER");
//        };
//    }
}
