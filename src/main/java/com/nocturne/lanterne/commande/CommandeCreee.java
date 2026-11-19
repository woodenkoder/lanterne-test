package com.nocturne.lanterne.commande;

import java.math.BigDecimal;

/** La réponse à une commande créée. */
public record CommandeCreee(long id, String statut, BigDecimal total) {}
