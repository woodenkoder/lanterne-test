package com.nocturne.lanterne.commande;

import java.math.BigDecimal;

/** Une commande enregistrée. */
public record Commande(Long id, String client, BigDecimal total, String statut) {}
