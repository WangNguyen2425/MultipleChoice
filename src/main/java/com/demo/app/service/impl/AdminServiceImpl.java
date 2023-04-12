package com.demo.app.service.impl;

import com.demo.app.model.Admin;
import com.demo.app.repository.AdminRepository;
import com.demo.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public <S extends Admin> S save(S entity) {
        return adminRepository.save(entity);
    }

    @Override
    public Optional<Admin> findById(Integer id) {
        return adminRepository.findById(id);
    }
}
