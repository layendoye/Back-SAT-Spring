package com.frontSAT.fSAT.repository;


import com.frontSAT.fSAT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);//possible de faire findByLoginAndPassword
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    //@Query("SELECT u FROM User u WHERE u.login IS Null")
    //public List<User>user();//retourne les users dont le user est null
}