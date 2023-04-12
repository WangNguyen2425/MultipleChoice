package com.demo.app.config;

import com.demo.app.model.Admin;
import com.demo.app.model.Role;
import com.demo.app.repository.AdminRepository;
import com.demo.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadDatabase implements CommandLineRunner {


    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public LoadDatabase(AdminRepository adminRepository, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public void run(String... args) {
        List<Role> roles = roleRepository.findAll();
        List<Admin> admins = adminRepository.findAll();
        if (roles.size() == 0){
            roleRepository.save(new Role("ROLE_ADMIN", false));
            roleRepository.save(new Role("ROLE_TEACHER", false));
            roleRepository.save(new Role("ROLE_STUDENT", false));
        }
        if (admins.size() == 0){
            Role role = roleRepository.findById(1).orElse(null);
            adminRepository.save(new Admin("admin", new BCryptPasswordEncoder().encode("admin") , false, role));
        }
    }
}
