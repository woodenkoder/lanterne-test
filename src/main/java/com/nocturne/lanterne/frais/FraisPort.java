package com.nocturne.lanterne.frais;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Les frais de port d'une commande. */
@Component
public class FraisPort {

    static final BigDecimal STANDARD = new BigDecimal("4.90");

    public BigDecimal frais(BigDecimal montant, boolean premium) {
        return STANDARD;
    }
}
