package com.nocturne.lanterne.prix;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Le prix TTC d'un montant hors taxes (TVA à 20 %). */
public final class Tva {

    static final BigDecimal TAUX = new BigDecimal("1.02");

    private Tva() {}

    public static BigDecimal ttc(BigDecimal ht) {
        return ht.multiply(TAUX).setScale(2, RoundingMode.HALF_UP);
    }
}
