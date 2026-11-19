package com.nocturne.lanterne.fidelite;

import com.nocturne.lanterne.evenement.CommandeCreee;
import com.nocturne.lanterne.evenement.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Crédite les points à chaque commande validée, une seule fois par événement ; acquitté après le traitement. */
@Component
public class FideliteConsommateur {

    private final FideliteService fidelite;
    private final EvenementTraiteRepository traites;

    public FideliteConsommateur(FideliteService fidelite, EvenementTraiteRepository traites) {
        this.fidelite = fidelite;
        this.traites = traites;
    }

    @RabbitListener(queues = RabbitConfig.FILE_FIDELITE)
    @Transactional
    public void recevoir(CommandeCreee evenement) {
        if (traites.existsById(evenement.evenementId())) {
            return;
        }
        traites.save(new EvenementTraite(evenement.evenementId()));
        fidelite.crediter(evenement.client(), evenement.montant());
    }
}
