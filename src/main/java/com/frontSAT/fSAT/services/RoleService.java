package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Role;
import com.frontSAT.fSAT.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
