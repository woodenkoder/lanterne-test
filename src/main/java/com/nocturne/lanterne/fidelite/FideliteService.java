package com.nocturne.lanterne.fidelite;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Les points de fidélité : 1 point par euro entier dépensé. */
@Service
public class FideliteService {

    private final CompteFideliteRepository comptes;

    public FideliteService(CompteFideliteRepository comptes) {
        this.comptes = comptes;
    }

    @Transactional(readOnly = true)
    public int points(String client) {
        return comptes.findById(client).map(CompteFidelite::getPoints).orElse(0);
    }

    @Transactional
    public void crediter(String client, BigDecimal montant) {
        CompteFidelite compte = comptes.findById(client).orElseGet(() -> comptes.save(new CompteFidelite(client)));
        compte.crediter(montant.setScale(0, RoundingMode.DOWN).intValue());
    }
}
