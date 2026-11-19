package com.nocturne.lanterne;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CommandeControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void une_commande_valide_est_creee() throws Exception {
        mvc.perform(post("/api/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"client": "ada@exemple.test", "lignes": [{"produitId": 1, "quantite": 2}]}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"));
    }
}
