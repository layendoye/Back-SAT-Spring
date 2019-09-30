package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Role;
import com.frontSAT.fSAT.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}
