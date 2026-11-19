package com.nocturne.lanterne.commande;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Au-delà de 5000 €, la validation échoue : la transaction est annulée. */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PlafondDepasseException extends RuntimeException {

    public PlafondDepasseException(Long id) {
        super("La commande " + id + " dépasse le plafond de validation");
    }
}
