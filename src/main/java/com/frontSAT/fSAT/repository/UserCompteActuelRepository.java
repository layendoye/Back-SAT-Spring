package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.UserCompteActuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Repository
public interface UserCompteActuelRepository extends JpaRepository<UserCompteActuel, Integer> {
    public List<UserRepository>findUserCompteActuelByUser();//retourne les users dont le user est null
}