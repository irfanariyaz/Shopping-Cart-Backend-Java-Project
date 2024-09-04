package com.irfanacode.dreamshops.data;

import com.irfanacode.dreamshops.model.Role;
import com.irfanacode.dreamshops.model.User;
import com.irfanacode.dreamshops.repository.RoleRepository;
import com.irfanacode.dreamshops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional//make single unit transaction
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
        private  final UserRepository userRepository;
        private  final PasswordEncoder passwordEncoder;
        private  final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
       createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }
    private void  createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
       for(int i =1;i<=5;i++){
           String defaultEmail = "user"+i+"@example.com";
           if(userRepository.existsByEmail(defaultEmail)){
               continue;
           }
           User user = new User();
           user.setFirstName("User"+i);
           user.setLastName("Lastname"+i);
           user.setEmail(defaultEmail);
           user.setRoles(Set.of(userRole));
           user.setPassword(passwordEncoder.encode("123456"));
           userRepository.save(user);
           System.out.println("Default user "+i + " created successfully");

       }
    }

    private void  createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        // Create default admin user
        for(int i =1;i<=2;i++){
            String defaultEmail = "Admin"+i+"@example.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin"+i);
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Default admin user "+i + " created successfully");

        }
    }
    private  void createDefaultRoleIfNotExists(Set<String> roles) {
        // Implement if needed
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty()) // Check if the role doesn't exist (using the role name
                .map(Role::new).forEach(roleRepository::save);
    }  // Other methods
}

