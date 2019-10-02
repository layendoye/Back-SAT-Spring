package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);//possible de faire findByLoginAndPassword
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.entreprise.id = :id1 AND u.id <> :id2")
    List<User>findUserEntreprise(@Param("id1")int idEntreprise,@Param("id2")int idUser);//retourne les users dont le user est null

    List<User>findUsersByEntreprise(Entreprise entreprise);

}