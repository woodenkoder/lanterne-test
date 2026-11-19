package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * La fausse banque, en attendant la vraie : elle accepte toutes les cartes, sauf 4000 0000 0000 0002 (refusée),
 * comme les cartes de test des vrais services de paiement.
 */
@Component
public class BanqueDeDemo implements Banque {

    public static final String CARTE_REFUSEE = "4000000000000002";

    @Override
    public boolean payer(String carte, BigDecimal montant) {
        return !carte.replace(" ", "").equals(CARTE_REFUSEE);
    }
}
