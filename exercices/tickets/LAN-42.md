# LAN-42 · Annuler une commande pas encore expédiée

**Demandé par :** Inès, cheffe de produit
**Priorité :** haute (12 appels au support la semaine dernière)

Aujourd'hui, un client qui se trompe doit appeler le support pour annuler sa commande.

**Ce qu'on veut :** un client peut annuler sa commande tant qu'elle n'est pas expédiée
(`POST /api/commandes/{id}/annulation`).

**Critères d'acceptation**

- Une commande en attente ou payée est annulée : 200, statut `ANNULEE`.
- Une commande expédiée ou livrée ne peut plus l'être : 409, au format standard des erreurs.
- Une commande inconnue : 404.
