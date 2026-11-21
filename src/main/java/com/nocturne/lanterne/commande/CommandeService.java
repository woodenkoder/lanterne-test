package com.nocturne.lanterne.commande;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/** Les règles des commandes. Pour l'instant, les commandes sont gardées en mémoire (la base arrive à la Cave). */
@Service
public class CommandeService {

    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, NouvelleCommande> commandes = new ConcurrentHashMap<>();
    private final Map<Long, StatutCommande> statuts = new ConcurrentHashMap<>();

    public CommandeCreee creer(NouvelleCommande commande) {
        long id = sequence.incrementAndGet();
        commandes.put(id, commande);
        statuts.put(id, StatutCommande.EN_ATTENTE);
        return new CommandeCreee(id, StatutCommande.EN_ATTENTE.name());
    }

    /** Le statut avance (paiement, expédition, livraison) : appelé par la logistique. */
    public void avancer(long id, StatutCommande statut) {
        if (!statuts.containsKey(id)) {
            throw new CommandeInconnueException(id);
        }
        statuts.put(id, statut);
    }

    /** LAN-42 : le client annule sa commande. */
    public CommandeCreee annuler(long id) {
        StatutCommande statut = statuts.get(id);
        if (statut == null) {
            throw new CommandeInconnueException(id);
        }
        if (statut == StatutCommande.EXPEDIEE || statut == StatutCommande.LIVREE) {
            throw new CommandeNonAnnulableException(id, statut);
        }
        statuts.put(id, StatutCommande.ANNULEE);
        return new CommandeCreee(id, StatutCommande.ANNULEE.name());
    }
}
