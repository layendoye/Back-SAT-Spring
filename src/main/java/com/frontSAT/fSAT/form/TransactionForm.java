package com.frontSAT.fSAT.form;

public class TransactionForm {
    private String nomClientEmetteur;
    private String telephoneEmetteur;
    private String nciEmetteur;
    private long montant;
    private String nomClientRecepteur;
    private String telephoneRecepteur;

    private String nciRecepteur;
    private String code;

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

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
