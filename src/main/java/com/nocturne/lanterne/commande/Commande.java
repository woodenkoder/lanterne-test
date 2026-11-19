package com.nocturne.lanterne.commande;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private String client;

    private LocalDateTime dateCommande;

    private BigDecimal montant;

    protected Commande() {}

    public Commande(String reference, String client, LocalDateTime dateCommande, BigDecimal montant) {
        this.reference = reference;
        this.client = client;
        this.dateCommande = dateCommande;
        this.montant = montant;
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public String getClient() {
        return client;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public BigDecimal getMontant() {
        return montant;
    }
}
