package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCompteActuelService {
    @Autowired
    CompteRepository compteRepository;

    public Compte save(Compte compte){
        return compteRepository.save(compte);
    }

    public List<Compte> findAll(){
        return compteRepository.findAll();
    }
    public Optional<Compte> findById(int id){
        return compteRepository.findById(id);
    }
    public Optional<Compte> findByNumeroCompte(String numeroCompte){
        return compteRepository.findCompteByNumeroCompte(numeroCompte);
    }
}
