package com.nocturne.lanterne.fidelite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CompteFidelite {

    @Id
    private String client;

    private int points;

    protected CompteFidelite() {}

    public CompteFidelite(String client) {
        this.client = client;
    }

    void crediter(int nouveaux) {
        points += nouveaux;
    }

    public int getPoints() {
        return points;
    }
}
