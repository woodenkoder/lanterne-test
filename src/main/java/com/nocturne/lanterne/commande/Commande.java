package com.nocturne.lanterne.commande;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Commande {

    @Id
    @GeneratedValue
    private Long id;

    private String client;

    private BigDecimal montant;

    private String statut = "EN_ATTENTE";

    protected Commande() {}

    public Commande(String client, BigDecimal montant) {
        this.client = client;
        this.montant = montant;
    }

    void valider() {
        statut = "VALIDEE";
    }

    public Long getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public String getStatut() {
        return statut;
    }
}
