package com.demo.app.service;

import com.demo.app.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> findAll();

    <S extends Admin> S save(S entity);

    Optional<Admin> findById(Integer id);
}
