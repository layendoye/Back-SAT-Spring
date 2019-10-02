package com.frontSAT.fSAT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class UserCompteActuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne(optional=false)
    @JsonIgnoreProperties({"nom","username","email","telephone","nci","image","status","roles"})
    private User user;

    @JoinColumn(name = "compte_id",referencedColumnName = "id")
    @ManyToOne(optional=false)

    private Compte compte;

    private Date dateAffectation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }
}
