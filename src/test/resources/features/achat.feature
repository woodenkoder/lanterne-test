# language: fr
Fonctionnalité: Acheter dans la boutique de Nocturne

  Scénario: Une commande payée
    Étant donné une commande de 42.00 € pour "ada@exemple.test"
    Quand le client paie avec la carte "4242 4242 4242 4242"
    Alors la commande est au statut "PAYEE"

  Scénario: Un paiement refusé
    Étant donné une commande de 42.00 € pour "ada@exemple.test"
    Quand le client paie avec la carte "4000 0000 0000 0002"
    Alors la commande est au statut "EN_ATTENTE_DE_PAIEMENT"
    Et le client lit le message "Paiement refusé"
