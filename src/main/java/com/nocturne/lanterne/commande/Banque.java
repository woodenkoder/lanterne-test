package com.nocturne.lanterne.commande;

import java.math.BigDecimal;

/** La banque qui accepte ou refuse un paiement. */
public interface Banque {

    boolean payer(String carte, BigDecimal montant);
}
