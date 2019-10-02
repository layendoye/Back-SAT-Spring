package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.model.UserCompteActuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Repository
public interface UserCompteActuelRepository extends JpaRepository<UserCompteActuel, Integer> {
    public List<UserCompteActuel>findUserCompteActuelByUser(User user);//retourne les users dont le user est null

    @Query("SELECT u FROM UserCompteActuel u WHERE u.compte.entreprise.id = :idEntreprise")
    public List<UserCompteActuel>findByEntreprise(@Param("idEntreprise") int id);
}