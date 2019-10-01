package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
    @Query("SELECT e FROM Entreprise e WHERE e.raisonSociale <> 'SA Transfert' AND e.raisonSociale <> 'Etat du Sénégal' ")
    public List<Entreprise> findPartenaire();

}