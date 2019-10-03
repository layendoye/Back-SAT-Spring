package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Tarifs;
import com.frontSAT.fSAT.repository.CompteRepository;
import com.frontSAT.fSAT.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifService {
    @Autowired
    TarifRepository tarifRepository;

    public Tarifs save(Tarifs tarifs){
        return tarifRepository.save(tarifs);
    }

    public List<Tarifs> findAll(){
        return tarifRepository.findAll();
    }
    public Optional<Tarifs> findById(int id){
        return tarifRepository.findById(id);
    }
}
