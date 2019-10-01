package com.frontSAT.fSAT.controller;

import com.frontSAT.fSAT.form.AffectationCompte;
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
    @Autowired
    UserCompteActuelService userCompteActuelService;

    @GetMapping(value = "/entreprises/liste")
    public List<Entreprise> lister(){
        return entrepriseService.findPartenaire();
    }
    @GetMapping(value = "/entreprise/{id}")
    public Entreprise lister(@PathVariable int id) throws Exception {
        Entreprise entreprise = entrepriseService.findById(id).orElseThrow(
                ()->new Exception("Cette entreprise n'existe pas !")
        );
        return entreprise;
    }
    @PostMapping(value = "/partenaires/add")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public MessageCompte addPartenaire(RegistrationEntrep registrationEntrep) {//regarder les transactions
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

        String msg="Le partenaire  "+ entreprise.getRaisonSociale()+" ainsi que son admin principal ont bien été ajouté !! ";
        String msgCompte="Le compte numéro "+compte.getNumeroCompte()+"  lui a été assigné";
        MessageCompte message=new MessageCompte(200,msg,msgCompte);
        return message;
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

    @GetMapping(value = "/bloque/entreprises/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public Message bloqueEntrep(@PathVariable int id) throws Exception{
        Entreprise entreprise=entrepriseService.findById(id).orElseThrow(
                ()-> new Exception("Ce partenaire n'existe pas !!")
        );
        if(entreprise.getRaisonSociale().equals("SA Transfert") || entreprise.getRaisonSociale().equals("Etat du Sénégal")){
            throw new Exception("Impossible de le bloquer !");
        }
        String msg;
        if(entreprise.getStatus().equals("Actif")){
            entreprise.setStatus("Bloqué");
            msg="Bloqué";
        }
        else {
            entreprise.setStatus("Actif");
            msg="Débloqué";
        }
        Message message=new Message(200,msg);
        return message;
    }

    @GetMapping(value = "/bloque/user/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin','ROLE_admin_Principal','ROLE_admin')")
    public Message bloqueUser(@PathVariable int id) throws Exception{
        User user=userService.findById(id).orElseThrow(
                ()-> new Exception("Cet utilisateur n'existe pas !!")
        );
        User userConnecte=userDetailsService.getUserConnecte();
        if(userConnecte==user){
            throw new Exception("Impossible de se bloquer soit même !");
        }
        else if(user.getEntreprise()!=userConnecte.getEntreprise()){
            throw new Exception("Impossible de bloquer cet utilisateur !");
        }
        else if(user.getId()==1){
            throw new Exception("Impossible de bloquer le super-admin principal !");
        }
        /*if(userConnecte.getRoles()[0]=='ROLE_admin' && user.getRoles()[0]=='ROLE_admin-Principal'){
            throw new Exception("Impossible de bloquer l' admin principal !");
        }*/
        String msg;
        if(user.getStatus().equals("Actif")){
            user.setStatus("Bloqué");
            msg="Bloqué";
        }
        else {
            user.setStatus("Actif");
            msg="Débloqué";
        }
        Message message=new Message(200,msg);
        return message;
    }

    @GetMapping(value = "/nouveau/compte/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public MessageCompte addCompte(@PathVariable int id) throws Exception{
        Entreprise entreprise=entrepriseService.findById(id).orElseThrow(
                ()-> new Exception("Ce partenaire n'existe pas !!")
        );
        if(entreprise.getRaisonSociale().equals("SA Transfert") || entreprise.getRaisonSociale().equals("Etat du Sénégal")){
            throw new Exception("Impossible de lui ajouter un compte !");
        }
        Compte compte=new Compte();
        SimpleDateFormat formater = new SimpleDateFormat("yyMM ddhh mmss");//2109 0225 1763
        Date now=new Date();
        String numCompte=formater.format(now);
        compte.setNumeroCompte(numCompte);
        compte.setEntreprise(entreprise);
        compte.setSolde(0);
        compteService.save(compte);
        String msg="Un nouveau compte est créé pour l'entreprise "+ entreprise.getRaisonSociale();
        String msgCompte=compte.getNumeroCompte();
        MessageCompte message=new MessageCompte(200,msg,msgCompte);
        return message;
    }

    @PostMapping(value = "/changer/compte", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_admin-Principal','ROLE_admin')")
    public Message changeCompte(@RequestBody AffectationCompte affectationCompte) throws Exception {

        Compte compte=compteService.findById(affectationCompte.getCompte()).orElseThrow(
                ()-> new Exception("Ce compte n'existe pas !")
        );
        User user=userService.findById(affectationCompte.getUtilisateur()).orElseThrow(
                ()-> new Exception("Cet utilisateur n'existe pas !")
        );
        /*if($user->getRoles()[0]=='ROLE_Super-admin' || $user->getRoles()[0]=='ROLE_Caissier'){
            throw new HttpException(403,'Impossible d\'affecter un compte à et utilisateur !');
        }*/
        User userconnecte=userDetailsService.getUserConnecte();
        if(user.getEntreprise().getId()!=userconnecte.getEntreprise().getId()){
            throw new Exception("Cet utilisateur n'appartient pas à votre entreprise !");
        }
        else if(compte.getEntreprise().getId()!=userconnecte.getEntreprise().getId()){
            throw new Exception("Ce compte n'appartient pas à votre entreprise !");
        }
        int idcompActuel=0;
        List<UserCompteActuel> userComp=userCompteActuelService.findUserCompteActuelByUser(user);
        if(userComp.size()!=0){
            idcompActuel=userComp.get(userComp.size()-1).getCompte().getId();//l id du compte qu il utilise actuellement
        }
        if(idcompActuel==compte.getId()){
            throw new Exception("Cet utilisateur utilise ce compte actuellement!");
        }
        UserCompteActuel userCompte=new UserCompteActuel();
        userCompte.setCompte(compte);
        userCompte.setUser(user);
        userCompte.setDateAffectation(new Date());
        userCompteActuelService.save(userCompte);
        return new Message(201,"Le compte de l'utilisateur a été modifié !!");
    }

    @GetMapping(value = "/compte/entreprise/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin','ROLE_admin_Principal','ROLE_admin')")
    public List<Compte> getCompte(@PathVariable int id) throws Exception {
        Entreprise entreprise = entrepriseService.findById(id).orElseThrow(
                ()->new Exception("Cette entreprise n'existe pas !")
        );
        return entreprise.getComptes();
    }

    @GetMapping(value = "/comptes/all")
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin')")
    public List<Compte> getAllCompte(){
        return compteService.findAll();
    }


}
