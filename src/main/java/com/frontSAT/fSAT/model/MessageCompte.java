package com.frontSAT.fSAT.model;

public class MessageCompte {
    private int status;
    private String message;
    private String compte;

    public MessageCompte() {
    }

    public MessageCompte(int status, String message, String compte) {
        this.status = status;
        this.message = message;
        this.compte = compte;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }
}
