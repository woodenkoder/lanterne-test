package com.nocturne.lanterne.livraison;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LivraisonService {

    static final String REPLI = "livraison sous 3 à 5 jours";
    private static final Logger LOG = LoggerFactory.getLogger(LivraisonService.class);

    private final TransporteurClient transporteur;

    public LivraisonService(TransporteurClient transporteur) {
        this.transporteur = transporteur;
    }

    /** Le délai annoncé au client ; si le transporteur ne répond pas, une estimation prudente. */
    @CircuitBreaker(name = "transporteur", fallbackMethod = "repli")
    public String delai(String codePostal) {
        return "livraison sous " + transporteur.delaiJours(codePostal) + " jours";
    }

    String repli(String codePostal, Throwable cause) {
        LOG.warn("Transporteur indisponible ({}) : délai de repli annoncé", cause.toString());
        return REPLI;
    }
}
