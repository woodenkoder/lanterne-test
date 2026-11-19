package com.nocturne.lanterne.bdd;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Etantdonné;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

/** Les étapes du parcours d'achat, qui appellent la vraie API de la Lanterne. */
public class AchatEtapes {

    @LocalServerPort
    int port;

    private Map<?, ?> commande;

    private RestClient api() {
        return RestClient.create("http://localhost:" + port);
    }

    @Etantdonné("une commande de {bigdecimal} € pour {string}")
    public void une_commande(BigDecimal montant, String client) {
        commande = api().post().uri("/api/commandes")
                .body(Map.of("client", client, "montant", montant))
                .retrieve().body(Map.class);
    }

    @Quand("le client paie avec la carte {string}")
    public void payer(String carte) {
        commande = api().post().uri("/api/commandes/{id}/paiement", commande.get("id"))
                .body(Map.of("carte", carte))
                .retrieve().body(Map.class);
    }

    @Alors("la commande est au statut {string}")
    public void statut(String attendu) {
        Map<?, ?> relue = api().get().uri("/api/commandes/{id}", commande.get("id")).retrieve().body(Map.class);
        assertThat(relue.get("statut")).isEqualTo(attendu);
    }

    @Et("le client lit le message {string}")
    public void message(String attendu) {
        assertThat(commande.get("message")).isEqualTo(attendu);
    }
}
