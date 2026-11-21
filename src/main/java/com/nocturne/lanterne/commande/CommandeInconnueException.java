package com.nocturne.lanterne.commande;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

/** Aucune commande avec cet identifiant : 404, au format standard des erreurs. */
public class CommandeInconnueException extends ErrorResponseException {
    public CommandeInconnueException(long id) {
        super(HttpStatus.NOT_FOUND, ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Commande inconnue : " + id), null);
    }
}
