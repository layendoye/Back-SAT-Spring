package com.frontSAT.fSAT.controller;

import com.frontSAT.fSAT.model.RegistrationUser;
import com.frontSAT.fSAT.model.Role;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.services.RoleService;
import com.frontSAT.fSAT.services.UserDetailsServiceImpl;
import com.frontSAT.fSAT.services.UserPrinciple;
import com.frontSAT.fSAT.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin

public class SecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    RoleService roleService;

    @PostMapping(value = "/inscription", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_Super_admin','ROLE_admin_Principal','ROLE_admin')")
    public User inscriptionUtilisateur(@RequestBody RegistrationUser registrationUser) throws Exception {
        if(registrationUser.getPassword().equals(registrationUser.getConfirmPassword())==false){
            throw new Exception("Les deux mot de passe ne correspondent pas");
        }
        User user=new User(registrationUser.getNom(),registrationUser.getUsername(),registrationUser.getEmail(),registrationUser.getPassword(),registrationUser.getTelephone(),registrationUser.getNci(),registrationUser.getImage(),"Actif");
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role=new Role();
        role.setId(registrationUser.getProfil());//l id sera envoyé grace au value du select
        roles.add(role);
        user.setRoles(roles);
        return userService.save(user);
    }

    @PostMapping(value = "/user/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User updateUser(@RequestBody RegistrationUser updateUser, @PathVariable int id) throws Exception {
        User userConnecte=userDetailsService.getUserConnecte();
        if(updateUser.getPassword().equals(updateUser.getConfirmPassword())==false){
            throw new Exception("Les deux mot de passe ne correspondent pas");
        }
        if(userService.findById(id)==null){
            throw new Exception("Cet utilisateur n'existe pas !");
        }


        if(id==1 && userConnecte.getId()!=1){
            throw new Exception("Vous ne pouvez pas modifier les informations de l'admin principal !");
        }
        User user=new User(updateUser.getNom(),updateUser.getUsername(),updateUser.getEmail(),updateUser.getPassword(),updateUser.getTelephone(),updateUser.getNci(),updateUser.getImage(),"Actif");
        user.setId(id);
        user.setNom(updateUser.getNom());
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        user.setPassword(encoder.encode(updateUser.getPassword()));
        user.setTelephone(updateUser.getTelephone());
        user.setNci(updateUser.getNci());
        if(updateUser.getImage()!=null){//s il ne change pas sa photo
            user.setImage(updateUser.getImage());
        }

        Set<Role> roles = new HashSet<>();
        Role role=new Role();
        role.setId(updateUser.getProfil());//l id sera envoyé grace au value du select
        roles.add(role);
        user.setRoles(roles);
        return userService.save(user);
    }

    @GetMapping(value = "/liste")
    public List<Role> profil(){
        return roleService.findAll();
    }

    @GetMapping(value = "/userConnecte")
    public User userConnecte(){
        return userDetailsService.getUserConnecte();
    }
}