package com.nocturne.lanterne.commande;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String client;

    private BigDecimal montant;

    protected Commande() {}

    public Commande(String client, BigDecimal montant) {
        this.client = client;
        this.montant = montant;
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
}
