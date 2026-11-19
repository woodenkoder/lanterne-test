package com.nocturne.lanterne.evenement;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * L'événement publié quand une commande est validée, en JSON :
 * {"evenementId": "…", "commandeId": 1, "client": "ada@exemple.test", "montant": 42.00}.
 * evenementId est unique : c'est lui qui permet de reconnaître un message reçu deux fois.
 */
public record CommandeCreee(UUID evenementId, long commandeId, String client, BigDecimal montant) {}
