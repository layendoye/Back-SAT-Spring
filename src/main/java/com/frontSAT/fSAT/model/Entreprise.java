package com.frontSAT.fSAT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data//Pour la serialisation à double sens
//Pour la serialisation à double sens
@EqualsAndHashCode(exclude = {"users", "comptes"})//Pour la serialisation à double sens
@Table(name = "entreprise", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "ninea"
        })
})
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 50)
    private String raisonSociale;

    @NotBlank
    @Size(min=3, max = 50)
    private String ninea;

    @NotBlank
    @Size(min=3, max = 50)
    private String adresse;

    private String status;

    @NotBlank
    @Size(min=3, max = 50)
    private String telephoneEntreprise;

    @NotBlank
    @Size(min=3, max = 50)
    private String emailEntreprise;

    @OneToMany(mappedBy ="entreprise")
    @JsonIgnoreProperties("entreprise")//Pour la serialisation à double sens (l objet service dans la classe user)
    public List<User> users;

    @OneToMany(mappedBy ="entreprise")
    @JsonIgnoreProperties("entreprise")//Pour la serialisation à double sens (l objet service dans la classe user)
    public List<Compte> comptes;
    public Entreprise() {}

    public Entreprise(@NotBlank @Size(min = 3, max = 50) String raisonSociale, @NotBlank @Size(min = 3, max = 50) String ninea, @NotBlank @Size(min = 3, max = 50) String adresse, @NotBlank @Size(min = 3, max = 50) String status, @NotBlank @Size(min = 3, max = 50) String telephoneEntreprise, @NotBlank @Size(min = 3, max = 50) String emailEntreprise) {
        this.raisonSociale = raisonSociale;
        this.ninea = ninea;
        this.adresse = adresse;
        this.status = status;
        this.telephoneEntreprise = telephoneEntreprise;
        this.emailEntreprise = emailEntreprise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephoneEntreprise() {
        return telephoneEntreprise;
    }

    public void setTelephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
    }

    public String getEmailEntreprise() {
        return emailEntreprise;
    }

    public void setEmailEntreprise(String emailEntreprise) {
        this.emailEntreprise = emailEntreprise;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
