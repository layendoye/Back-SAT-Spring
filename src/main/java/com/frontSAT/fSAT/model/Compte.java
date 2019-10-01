package com.frontSAT.fSAT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data//Pour la serialisation à double sens
@EqualsAndHashCode(exclude = "caissier")//Pour la serialisation à double sens
@Table(name = "compte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "numeroCompte"
        })
})
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 50)
    private String numeroCompte;

    private long solde;

    @JoinColumn(name = "entreprise_id",referencedColumnName = "id")
    @ManyToOne(optional=false)//plusieurs user une entreprise (optional=false pour dire que c est obligatoire)
    //Pour la serialisation à double sens (users qui est dans entreprise)
    @JsonIgnoreProperties({"comptes", "raisonSociale","ninea","adresse","status","telephoneEntreprise","users","emailEntreprise"})
    private Entreprise entreprise;

    public Compte() {
    }

    public Compte(@NotBlank @Size(min = 3, max = 50) String numeroCompte, @NotBlank long solde, Entreprise entreprise) {
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.entreprise = entreprise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public long getSolde() {
        return solde;
    }

    public void setSolde(long solde) {
        this.solde = solde;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}
