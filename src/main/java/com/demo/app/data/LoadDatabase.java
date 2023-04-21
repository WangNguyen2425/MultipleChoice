package com.demo.app.data;

import com.demo.app.config.PasswordEncoder;
import com.demo.app.model.Role;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class LoadDatabase implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void run(String... args) {
        long roleCount = roleRepository.count();
        if (roleCount == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_PRINCIPAL"));
            roleRepository.save(new Role("ROLE_TEACHER"));
            roleRepository.save(new Role("ROLE_STUDENT"));
            roleRepository.save(new Role("ROLE_USER"));
        }

        if(!userRepository.existsByUsername("admin")){
            User user = new User();
            List<Role> roles = roleRepository.findAll();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.passwordEncode().encode("admin"));
            user.setRoles(roles);
            userRepository.save(user);
        }

    }
}
