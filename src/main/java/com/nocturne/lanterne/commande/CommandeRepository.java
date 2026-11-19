package com.nocturne.lanterne.commande;

/** Là où les commandes sont gardées. */
public interface CommandeRepository {

    Commande enregistrer(Commande commande);

    long compterParClient(String client);
}
