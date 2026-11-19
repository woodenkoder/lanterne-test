package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/** Les prix des produits de Nocturne. En dur pour l'instant : la base arrive à la Cave. */
@Component
public class Catalogue {

    private static final Map<Long, BigDecimal> PRIX = Map.of(
            1L, new BigDecimal("12.50"),
            2L, new BigDecimal("30.00"),
            3L, new BigDecimal("4.90"));

    public BigDecimal prix(Long produitId) {
        BigDecimal prix = PRIX.get(produitId);
        if (prix == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produit inconnu : " + produitId);
        }
        return prix;
    }
}
