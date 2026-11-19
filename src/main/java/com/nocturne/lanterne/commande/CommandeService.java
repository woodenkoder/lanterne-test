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

    public CommandeCreee creer(NouvelleCommande commande) {
        long id = sequence.incrementAndGet();
        commandes.put(id, commande);
        return new CommandeCreee(id, "EN_ATTENTE");
    }
}
