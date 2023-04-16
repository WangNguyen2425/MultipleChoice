package com.demo.app.data;

import com.demo.app.model.Role;
import com.demo.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadDatabase implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public LoadDatabase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<Role> roles = roleRepository.findAll();
        if (roles.size() == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_TEACHER"));
            roleRepository.save(new Role("ROLE_STUDENT"));
        }
    }
}
