package com.frontSAT.fSAT.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data//Pour la serialisation à double sens
@EqualsAndHashCode(exclude = "entreprise")//Pour la serialisation à double sens
@Table(name = "transaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "code"
        })
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 50)
    private String nomClientEmetteur;

    @NotBlank
    @Size(min=3, max = 50)
    private String telephoneEmetteur;

    @NotBlank
    @Size(min=3, max = 50)
    private String nciEmetteur;

    private Date dateEnvoi;

    @Size(min=3, max = 50)
    private String code;

    @JoinColumn(name = "userComptePartenaireEmetteur",referencedColumnName = "id")
    @ManyToOne(optional=false)
    private UserCompteActuel userComptePartenaireEmetteur;

    private long montant;


    private int frais;


    @NotBlank
    @Size(min=3, max = 50)
    private String nomClientRecepteur;

    @NotBlank
    @Size(min=3, max = 50)
    private String telephoneRecepteur;


    private String nciRecepteur;


    private Date dateReception;

    @JoinColumn(name = "userComptePartenaireRecepteur",referencedColumnName = "id")
    @ManyToOne(optional=true)
    private UserCompteActuel userComptePartenaireRecepteur;


    private long commissionEmetteur;


    private long commissionRecepteur;


    private long commissionWari;


    private long taxesEtat;


    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomClientEmetteur() {
        return nomClientEmetteur;
    }

    public void setNomClientEmetteur(String nomClientEmetteur) {
        this.nomClientEmetteur = nomClientEmetteur;
    }

    public String getTelephoneEmetteur() {
        return telephoneEmetteur;
    }

    public void setTelephoneEmetteur(String telephoneEmetteur) {
        this.telephoneEmetteur = telephoneEmetteur;
    }

    public String getNciEmetteur() {
        return nciEmetteur;
    }

    public void setNciEmetteur(String nciEmetteur) {
        this.nciEmetteur = nciEmetteur;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserCompteActuel getUserComptePartenaireEmetteur() {
        return userComptePartenaireEmetteur;
    }

    public void setUserComptePartenaireEmetteur(UserCompteActuel userComptePartenaireEmetteur) {
        this.userComptePartenaireEmetteur = userComptePartenaireEmetteur;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }

    public int getFrais() {
        return frais;
    }

    public void setFrais(int frais) {
        this.frais = frais;
    }

    public String getNomClientRecepteur() {
        return nomClientRecepteur;
    }

    public void setNomClientRecepteur(String nomClientRecepteur) {
        this.nomClientRecepteur = nomClientRecepteur;
    }

    public String getTelephoneRecepteur() {
        return telephoneRecepteur;
    }

    public void setTelephoneRecepteur(String telephoneRecepteur) {
        this.telephoneRecepteur = telephoneRecepteur;
    }

    public String getNciRecepteur() {
        return nciRecepteur;
    }

    public void setNciRecepteur(String nciRecepteur) {
        this.nciRecepteur = nciRecepteur;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public UserCompteActuel getUserComptePartenaireRecepteur() {
        return userComptePartenaireRecepteur;
    }

    public void setUserComptePartenaireRecepteur(UserCompteActuel userComptePartenaireRecepteur) {
        this.userComptePartenaireRecepteur = userComptePartenaireRecepteur;
    }

    public long getCommissionEmetteur() {
        return commissionEmetteur;
    }

    public void setCommissionEmetteur(long commissionEmetteur) {
        this.commissionEmetteur = commissionEmetteur;
    }

    public long getCommissionRecepteur() {
        return commissionRecepteur;
    }

    public void setCommissionRecepteur(long commissionRecepteur) {
        this.commissionRecepteur = commissionRecepteur;
    }

    public long getCommissionWari() {
        return commissionWari;
    }

    public void setCommissionWari(long commissionWari) {
        this.commissionWari = commissionWari;
    }

    public long getTaxesEtat() {
        return taxesEtat;
    }

    public void setTaxesEtat(long taxesEtat) {
        this.taxesEtat = taxesEtat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
