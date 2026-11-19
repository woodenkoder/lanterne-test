package com.nocturne.lanterne.fidelite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

/** Un événement déjà traité : le recevoir une seconde fois ne change rien. */
@Entity
public class EvenementTraite {

    @Id
    private UUID id;

    protected EvenementTraite() {}

    public EvenementTraite(UUID id) {
        this.id = id;
    }
}
