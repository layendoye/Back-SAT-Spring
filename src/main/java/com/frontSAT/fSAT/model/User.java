package com.frontSAT.fSAT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data//Pour la serialisation à double sens
@EqualsAndHashCode(exclude = "entreprise")//Pour la serialisation à double sens
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 50)
    private String nom;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    @JsonIgnore
    private String password;

    @NotBlank
    @Size(min=2, max = 100)
    private String telephone;

    @NotBlank
    @Size(min=2, max = 100)
    private String nci;

    @Size(min=2, max = 100)
    private String image;

    @NotBlank
    @Size(min=2, max = 100)
    private String status;

    @JoinColumn(name = "entreprise_id",referencedColumnName = "id",nullable = false)
    @ManyToOne(optional=false)//plusieurs user une entreprise (optional=false pour dire que c est obligatoire)
    @JsonIgnoreProperties({"users","comptes","soldeGlobal"})//Pour la serialisation à double sens (users qui est dans entreprise)

    private Entreprise entreprise;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(@NotBlank @Size(min = 3, max = 50) String nom, @NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(min = 6, max = 100) String password, @NotBlank @Size(min = 2, max = 100) String telephone, @NotBlank @Size(min = 2, max = 100) String nci,
                @Size(min = 2, max = 100) String image, @NotBlank @Size(min = 2, max = 100) String status) {
        this.nom = nom;
        this.username = username;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.nci = nci;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNci() {
        return nci;
    }

    public void setNci(String nci) {
        this.nci = nci;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}