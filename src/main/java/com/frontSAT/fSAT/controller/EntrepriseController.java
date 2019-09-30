package com.frontSAT.fSAT.controller;

import com.frontSAT.fSAT.model.Entreprise;
import com.frontSAT.fSAT.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EntrepriseController {
    @Autowired
    EntrepriseService entrepriseService;

    @GetMapping(value = "/entreprises/liste")
    public List<Entreprise> lister(){
        return entrepriseService.findAll();
    }
    @GetMapping(value = "/entreprise/{id}")
    public Optional<Entreprise> lister(@PathVariable int id) throws Exception {
        Optional<Entreprise> entreprise = entrepriseService.findById(id);
        if(entreprise==null){
            throw new Exception("Cet utilisateur n'existe pas !");
        }
        return entreprise;
    }
}
