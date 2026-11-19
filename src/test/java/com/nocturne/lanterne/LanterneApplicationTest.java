package com.nocturne.lanterne;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/** La Lanterne démarre (avec une base H2 en mémoire pour les tests). */
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:lanterne;MODE=PostgreSQL", "spring.datasource.username=sa",
        "spring.datasource.password="})
class LanterneApplicationTest {

    @Test
    void demarre() {}
}
