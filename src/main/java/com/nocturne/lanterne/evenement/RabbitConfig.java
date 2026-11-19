package com.nocturne.lanterne.evenement;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Les noms à garder : l'échange, la clé de routage, la file (et sa file des messages en échec, à créer). */
@Configuration
public class RabbitConfig {

    public static final String ECHANGE = "lanterne.commandes";
    public static final String CLE_COMMANDE_CREEE = "commande.creee";
    public static final String FILE_FIDELITE = "fidelite.commande-creee";
    public static final String FILE_FIDELITE_ECHECS = "fidelite.commande-creee.dlq";

    @Bean
    TopicExchange echange() {
        return new TopicExchange(ECHANGE);
    }

    @Bean
    Queue fileFidelite() {
        return QueueBuilder.durable(FILE_FIDELITE)
                .deadLetterExchange("")
                .deadLetterRoutingKey(FILE_FIDELITE_ECHECS)
                .build();
    }

    /** Les messages qui échouent encore après 3 essais finissent ici, pour être examinés. */
    @Bean
    Queue fileFideliteEchecs() {
        return QueueBuilder.durable(FILE_FIDELITE_ECHECS).build();
    }

    @Bean
    Binding liaisonFidelite(Queue fileFidelite, TopicExchange echange) {
        return BindingBuilder.bind(fileFidelite).to(echange).with(CLE_COMMANDE_CREEE);
    }

    @Bean
    MessageConverter json() {
        return new Jackson2JsonMessageConverter();
    }
}
