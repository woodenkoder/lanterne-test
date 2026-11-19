package com.nocturne.lanterne.commande;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/** Les règles des commandes : total, remise fidélité, enregistrement. */
@Service
public class CommandeService {

    static final BigDecimal REMISE_FIDELITE = new BigDecimal("0.90");

    private final CommandeRepository repository;
    private final Catalogue catalogue;

    public CommandeService(CommandeRepository repository, Catalogue catalogue) {
        this.repository = repository;
        this.catalogue = catalogue;
    }

    public CommandeCreee creer(NouvelleCommande commande) {
        BigDecimal total = BigDecimal.ZERO;
        for (NouvelleCommande.Ligne ligne : commande.lignes()) {
            total = total.add(catalogue.prix(ligne.produitId()).multiply(BigDecimal.valueOf(ligne.quantite())));
        }
        if (repository.compterParClient(commande.client()) >= 3) {
            total = total.multiply(REMISE_FIDELITE);
        }
        total = total.setScale(2, RoundingMode.HALF_UP);

        Commande enregistree = repository.enregistrer(new Commande(null, commande.client(), total, "EN_ATTENTE"));
        return new CommandeCreee(enregistree.id(), enregistree.statut(), enregistree.total());
    }
}
