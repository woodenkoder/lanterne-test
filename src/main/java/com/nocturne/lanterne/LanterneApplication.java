package com.nocturne.lanterne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** La Lanterne : l'API de gestion de commandes de Nocturne. */
@SpringBootApplication
public class LanterneApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanterneApplication.class, args);
    }
}
