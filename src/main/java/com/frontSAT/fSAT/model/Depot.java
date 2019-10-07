package com.frontSAT.fSAT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data//Pour la serialisation à double sens
@EqualsAndHashCode(exclude = "entreprise")//Pour la serialisation à double sens
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;
    @Range(min=75000)
    private long montant;

    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToOne(optional=false)
    @JsonIgnoreProperties({"entreprise", "roles"})
    private User caissier;

    @JoinColumn(name = "compte_id",referencedColumnName = "id")
    @ManyToOne(optional=false)
    //@JsonIgnoreProperties("comptes")
    private Compte compte;

    public Depot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }

    public User getCaissier() {
        return caissier;
    }

    public void setCaissier(User caissier) {
        this.caissier = caissier;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
