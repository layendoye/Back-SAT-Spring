package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepotRepository extends JpaRepository<Depot, Integer> {

}