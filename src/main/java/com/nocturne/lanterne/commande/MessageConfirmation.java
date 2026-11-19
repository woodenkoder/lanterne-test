package com.nocturne.lanterne.commande;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;

/** Le message de confirmation envoyé au client, à partir d'un modèle « Merci ${client} ! ». */
@Component
public class MessageConfirmation {

    static final String MODELE = "Merci ${client} ! Ta commande ${id} est bien enregistrée.";

    public String pour(String client, long id) {
        return new StringSubstitutor(Map.of("client", client, "id", String.valueOf(id))).replace(MODELE);
    }
}
