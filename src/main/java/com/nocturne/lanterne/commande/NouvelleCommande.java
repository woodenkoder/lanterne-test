package com.nocturne.lanterne.commande;

import java.util.List;

/** Ce que le client envoie pour passer une commande. */
public record NouvelleCommande(String client, List<Ligne> lignes, String codePromo) {

    /** Une ligne de la commande : un produit et sa quantité. */
    public record Ligne(Long produitId, int quantite) {}
}
