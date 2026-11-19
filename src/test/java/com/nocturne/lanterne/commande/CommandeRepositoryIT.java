package com.nocturne.lanterne.commande;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/** Le repository contre un vrai PostgreSQL 17, avec les vraies migrations Flyway. */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CommandeRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired
    CommandeRepository commandes;

    @Autowired
    JdbcTemplate jdbc;

    @Test
    void les_migrations_flyway_sont_appliquees() {
        Integer reussies = jdbc.queryForObject(
                "select count(*) from flyway_schema_history where success and version = '1'", Integer.class);
        assertThat(reussies).isEqualTo(1);
    }

    @Test
    void les_dernieres_commandes_d_un_client_sont_triees_par_date() {
        LocalDateTime maintenant = LocalDateTime.now();
        commandes.save(new Commande("CMD-A", "ada", maintenant.minusDays(2), BigDecimal.ONE));
        commandes.save(new Commande("CMD-B", "ada", maintenant, BigDecimal.ONE));
        commandes.save(new Commande("CMD-C", "ada", maintenant.minusDays(1), BigDecimal.ONE));
        commandes.save(new Commande("CMD-D", "linus", maintenant, BigDecimal.ONE));

        assertThat(commandes.findTop10ByClientOrderByDateCommandeDesc("ada"))
                .extracting(Commande::getReference)
                .containsExactly("CMD-B", "CMD-C", "CMD-A");
    }

    @Test
    void deux_commandes_avec_la_meme_reference_sont_refusees() {
        commandes.saveAndFlush(new Commande("CMD-DOUBLE", "ada", LocalDateTime.now(), BigDecimal.ONE));
        assertThatThrownBy(() -> commandes.saveAndFlush(
                        new Commande("CMD-DOUBLE", "linus", LocalDateTime.now(), BigDecimal.TEN)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
