package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    public Optional<Compte> findCompteByNumeroCompte(String numeroCompte);
}