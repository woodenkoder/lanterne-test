package com.nocturne.lanterne.commande;

import com.nocturne.lanterne.evenement.CommandeCreee;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommandeService {

    static final BigDecimal PLAFOND = new BigDecimal("5000.00");

    private final CommandeRepository commandes;
    private final ApplicationEventPublisher evenements;

    public CommandeService(CommandeRepository commandes, ApplicationEventPublisher evenements) {
        this.commandes = commandes;
        this.evenements = evenements;
    }

    @Transactional
    public Commande creer(String client, BigDecimal montant) {
        return commandes.save(new Commande(client, montant));
    }

    /** Valide la commande. Au-delà du plafond, une exception annule toute la transaction. */
    @Transactional
    public Commande valider(Long id) {
        Commande commande = commandes.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        commande.valider();
        if (commande.getMontant().compareTo(PLAFOND) > 0) {
            throw new PlafondDepasseException(id);
        }
        // Annoncé dans l'application ; envoyé à RabbitMQ seulement après le commit (PublicationApresCommit).
        evenements.publishEvent(new CommandeCreee(UUID.randomUUID(), id, commande.getClient(), commande.getMontant()));
        return commande;
    }
}
