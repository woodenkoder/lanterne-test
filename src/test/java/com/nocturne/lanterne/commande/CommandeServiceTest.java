package com.nocturne.lanterne.commande;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandeServiceTest {

    CommandeRepository repository = mock(CommandeRepository.class);
    CommandeService service = new CommandeService(repository, new Catalogue());

    @BeforeEach
    void enregistrerRenvoieLaCommande() {
        when(repository.enregistrer(any())).thenAnswer(appel -> {
            Commande c = appel.getArgument(0);
            return new Commande(42L, c.client(), c.total(), c.statut());
        });
    }

    private NouvelleCommande commande(NouvelleCommande.Ligne... lignes) {
        return new NouvelleCommande("ada@exemple.test", List.of(lignes), null);
    }

    @Test
    void le_total_additionne_prix_fois_quantite() {
        CommandeCreee creee = service.creer(commande(new NouvelleCommande.Ligne(1L, 2), new NouvelleCommande.Ligne(2L, 1)));
        assertThat(creee.total()).isEqualByComparingTo("55.00");
        assertThat(creee.statut()).isEqualTo("EN_ATTENTE");
    }

    @Test
    void pas_de_remise_avant_la_quatrieme_commande() {
        when(repository.compterParClient("ada@exemple.test")).thenReturn(2L);
        assertThat(service.creer(commande(new NouvelleCommande.Ligne(2L, 1))).total()).isEqualByComparingTo("30.00");
    }

    @Test
    void remise_fidelite_a_partir_de_la_quatrieme_commande() {
        when(repository.compterParClient("ada@exemple.test")).thenReturn(3L);
        assertThat(service.creer(commande(new NouvelleCommande.Ligne(2L, 1))).total()).isEqualByComparingTo("27.00");
    }
}
