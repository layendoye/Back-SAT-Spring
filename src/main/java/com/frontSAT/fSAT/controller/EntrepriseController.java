package com.frontSAT.fSAT.controller;

import com.frontSAT.fSAT.form.DepotForm;
import com.frontSAT.fSAT.form.RegistrationEntrep;
import com.frontSAT.fSAT.model.*;
import com.frontSAT.fSAT.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class EntrepriseController {
    @Autowired
    EntrepriseService entrepriseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompteService compteService;
    @Autowired
    private DepotService depotService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping(value = "/entreprises/liste")
    public List<Entreprise> lister(){
        return entrepriseService.findPartenaire();
    }
    @GetMapping(value = "/entreprise/{id}")
    public Optional<Entreprise> lister(@PathVariable int id) throws Exception {
        Optional<Entreprise> entreprise = entrepriseService.findById(id);
        if(entreprise==null){
            throw new Exception("Cet utilisateur n'existe pas !");
        }
        return entreprise;
    }
    @PostMapping(value = "/partenaires/add")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public ResponseEntity<String> addPartenaire(RegistrationEntrep registrationEntrep) {//regarder les transactions
        Entreprise entreprise=new Entreprise(registrationEntrep.getRaisonSociale(),registrationEntrep.getNinea(),registrationEntrep.getAdresse(),"Actif",registrationEntrep.getTelephoneEntreprise(),registrationEntrep.getEmailEntreprise());
        entrepriseService.save(entreprise);
        Compte compte=new Compte();
        SimpleDateFormat formater = new SimpleDateFormat("yyMM ddhh mmss");//2109 0225 1763
        Date now=new Date();
        String numCompte=formater.format(now);
        compte.setNumeroCompte(numCompte);
        compte.setEntreprise(entreprise);
        compte.setSolde(0);
        compteService.save(compte);
        User user=new User(registrationEntrep.getNom(),registrationEntrep.getUsername(),registrationEntrep.getEmail(),registrationEntrep.getPassword(),registrationEntrep.getTelephone(),registrationEntrep.getNci(),registrationEntrep.getImage(),"Actif");
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role=new Role();
        role.setId(3);//admin partenaire
        roles.add(role);
        user.setRoles(roles);
        user.setEntreprise(entreprise);
        userService.save(user);
        /*$afficher = [
        $this->status => 201,
                $this->message => 'Le partenaire '.$entreprise->getRaisonSociale().' ainsi que son admin principal ont bien été ajouté !! ',
                $this->compteStr =>'Le compte numéro '.$compte->getNumeroCompte().' lui a été assigné'
            ]*/
        return new ResponseEntity<>("Enregistrer", HttpStatus.OK);
    }

    @PostMapping(value = "/partenaires/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public Entreprise updatePartenaire(@RequestBody Entreprise entreprise,@PathVariable int id) throws Exception {
        entreprise.setId(id);
        Entreprise entreprise1=entrepriseService.findById(id).orElseThrow(
                ()-> new Exception("L'entreprise avec l'id "+id+" n'existe pas !")
        );

        entreprise.setStatus(entreprise1.getStatus());
        entrepriseService.save(entreprise);
        return entreprise;
    }

    @PostMapping(value = "/nouveau/depot", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_Caissier')")
    public Depot depot(@RequestBody DepotForm depotForm) throws Exception {
        User caissier = userDetailsService.getUserConnecte();
        Depot depot=new Depot();
        depot.setCaissier(caissier);
        Compte compte=compteService.findByNumeroCompte(depotForm.getCompte()).orElseThrow(
                ()-> new Exception("Ce compte n'existe pas !")
        );
        if(compte.getEntreprise().getRaisonSociale().equals("SA Transfert") || compte.getEntreprise().getRaisonSociale().equals("Etat du Sénégal")){
            throw new Exception("On ne peut pas faire de depot dans ce compte!");
        }

        depot.setCompte(compte);
        depot.setDate(new Date());
        depot.setMontant(depotForm.getMontant());
        compte.setSolde(compte.getSolde()+depotForm.getMontant());
        compteService.save(compte);
        return depotService.save(depot);
    }

    @PostMapping(value = "/bloque/entreprises/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_Super-admin')")
    public Message bloqueEntrep(@PathVariable int id) throws Exception{
        Entreprise entreprise=entrepriseService.findById(id).orElseThrow(
                ()-> new Exception("Ce partenaire n'existe pas !!")
        );
        if(entreprise.getRaisonSociale().equals("SA Transfert") || entreprise.getRaisonSociale().equals("Etat du Sénégal")){
            throw new Exception("Impossible de le bloquer !");
        }
        String msg="";
        if(entreprise.getStatus().equals("Actif")){
            entreprise.setStatus("Bloqué");
            msg="Bloqué";
        }
        else {
            entreprise.setStatus("Actif");
            msg="Actif";
        }
        Message message=new Message(200,msg);
        return message;
    }

}
