package com.nocturne.lanterne.commande;

import java.math.BigDecimal;

/** Une commande, son statut (EN_ATTENTE_DE_PAIEMENT, PAYEE) et le dernier message pour le client. */
public record Commande(Long id, String client, BigDecimal montant, String statut, String message) {}
