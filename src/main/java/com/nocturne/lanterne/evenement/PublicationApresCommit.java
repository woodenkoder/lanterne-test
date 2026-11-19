package com.nocturne.lanterne.evenement;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/** Envoie l'événement à RabbitMQ une fois la transaction validée : jamais pour une commande annulée. */
@Component
public class PublicationApresCommit {

    private final RabbitTemplate rabbit;

    public PublicationApresCommit(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publier(CommandeCreee evenement) {
        rabbit.convertAndSend(RabbitConfig.ECHANGE, RabbitConfig.CLE_COMMANDE_CREEE, evenement);
    }
}
