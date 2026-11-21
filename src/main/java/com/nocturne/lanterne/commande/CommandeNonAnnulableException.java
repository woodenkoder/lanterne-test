package com.nocturne.lanterne.commande;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

/** La commande est déjà partie : on ne peut plus l'annuler (409, au format standard des erreurs). */
public class CommandeNonAnnulableException extends ErrorResponseException {
    public CommandeNonAnnulableException(long id, StatutCommande statut) {
        super(HttpStatus.CONFLICT, ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La commande " + id + " ne peut plus être annulée : elle est " + statut + "."), null);
    }
}
