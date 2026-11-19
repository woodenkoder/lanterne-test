package com.nocturne.lanterne.commande;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    /** Les 10 dernières commandes d'un client, de la plus récente à la plus ancienne. */
    List<Commande> findTop10ByClientOrderByDateCommandeDesc(String client);
}
