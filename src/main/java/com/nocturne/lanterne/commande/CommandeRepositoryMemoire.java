package com.nocturne.lanterne.commande;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

/** Les commandes gardées en mémoire, en attendant la base (la Cave). */
@Repository
class CommandeRepositoryMemoire implements CommandeRepository {

    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, Commande> commandes = new ConcurrentHashMap<>();

    @Override
    public Commande enregistrer(Commande commande) {
        long id = sequence.incrementAndGet();
        Commande enregistree = new Commande(id, commande.client(), commande.total(), commande.statut());
        commandes.put(id, enregistree);
        return enregistree;
    }

    @Override
    public long compterParClient(String client) {
        return commandes.values().stream().filter(c -> c.client().equals(client)).count();
    }
}
