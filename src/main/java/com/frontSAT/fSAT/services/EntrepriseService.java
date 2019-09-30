package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.repository.EntrepriseRepository;
import com.frontSAT.fSAT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {
    @Autowired
    EntrepriseRepository entrepriseRepository;

    public Entreprise save(Entreprise entreprise){

        return entrepriseRepository.save(entreprise);
    }

    public List<Entreprise> findAll(){
        return entrepriseRepository.findAll();
    }
    public Optional<Entreprise> findById(int id){
        return entrepriseRepository.findById(id);
    }
}
