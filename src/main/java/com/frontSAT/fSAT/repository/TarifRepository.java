package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Tarifs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TarifRepository extends JpaRepository<Tarifs, Integer> {

}