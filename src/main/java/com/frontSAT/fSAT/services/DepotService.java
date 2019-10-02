package com.frontSAT.fSAT.services;

import com.frontSAT.fSAT.model.Compte;
import com.frontSAT.fSAT.model.Depot;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.repository.CompteRepository;
import com.frontSAT.fSAT.repository.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepotService {
    @Autowired
    DepotRepository depotRepository;

    public Depot save(Depot depot){
        return depotRepository.save(depot);
    }

    public List<Depot> findAll(){
        return depotRepository.findAll();
    }
    public Optional<Depot> findById(int id){
        return depotRepository.findById(id);
    }
    public List<Depot> findMesDepots(User caissier, Compte compte){
        return depotRepository.findMesDepots(caissier,compte);
    }
    public List<Depot>findDepotsByCaissier(User caissier){
        return depotRepository.findDepotsByCaissier(caissier);
    }
}
