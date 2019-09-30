package com.frontSAT.fSAT.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data//Pour la serialisation à double sens
@Table(name = "tarifs")
public class Tarifs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private int borneInferieure;

    @NotBlank
    private int borneSuperieure;

    @NotBlank
    private int valeur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorneInferieure() {
        return borneInferieure;
    }

    public void setBorneInferieure(int borneInferieure) {
        this.borneInferieure = borneInferieure;
    }

    public int getBorneSuperieure() {
        return borneSuperieure;
    }

    public void setBorneSuperieure(int borneSuperieure) {
        this.borneSuperieure = borneSuperieure;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
