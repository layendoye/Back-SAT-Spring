package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.model.UserCompteActuel;
import com.frontSAT.fSAT.repository.CompteRepository;
import com.frontSAT.fSAT.repository.UserCompteActuelRepository;
import com.frontSAT.fSAT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCompteActuelService {
    @Autowired
    UserCompteActuelRepository userCompteActuelRepository;

    public UserCompteActuel save(UserCompteActuel userCompteActuel){
        return userCompteActuelRepository.save(userCompteActuel);
    }

    public List<UserCompteActuel> findAll(){
        return userCompteActuelRepository.findAll();
    }
    public Optional<UserCompteActuel> findById(int id){
        return userCompteActuelRepository.findById(id);
    }
    public List<UserCompteActuel>findUserCompteActuelByUser(User user){
        return userCompteActuelRepository.findUserCompteActuelByUser(user);
    }
    public List<UserCompteActuel>findByEntreprise(int id){
        return userCompteActuelRepository.findByEntreprise(id);
    }

}
