package com.nocturne.lanterne.commande;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

/** LAN-42 : les tests de l'annulation, presque tous prêts. */
class CommandeAnnulationTest {

    CommandeService service = new CommandeService();

    long nouvelleCommande() {
        return service.creer(new NouvelleCommande("ada@exemple.test", List.of(new NouvelleCommande.Ligne(1L, 2)), null))
                .id();
    }

    @Test
    void une_commande_en_attente_est_annulee() {
        long id = nouvelleCommande();
        assertThat(service.annuler(id).statut()).isEqualTo("ANNULEE");
    }

    @Test
    void une_commande_expediee_ne_peut_plus_etre_annulee() {
        long id = nouvelleCommande();
        service.avancer(id, StatutCommande.EXPEDIEE);
        assertThatThrownBy(() -> service.annuler(id)).isInstanceOf(CommandeNonAnnulableException.class);
    }

    @Test
    void une_commande_inconnue() {
        assertThatThrownBy(() -> service.annuler(404)).isInstanceOf(CommandeInconnueException.class);
    }
}
