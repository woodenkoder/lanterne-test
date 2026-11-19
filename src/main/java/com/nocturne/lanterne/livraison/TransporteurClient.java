package com.nocturne.lanterne.livraison;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/** Le client du transporteur : GET /delai?codePostal=… → {"jours": 2}, avec des timeouts. */
@Component
public class TransporteurClient {

    record Reponse(int jours) {}

    private final RestClient http;

    public TransporteurClient(RestClient.Builder builder, @Value("${lanterne.transporteur.url}") String url) {
        JdkClientHttpRequestFactory requetes = new JdkClientHttpRequestFactory(
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(1)).build());
        requetes.setReadTimeout(Duration.ofSeconds(2));
        this.http = builder.baseUrl(url).requestFactory(requetes).build();
    }

    public int delaiJours(String codePostal) {
        Reponse reponse = http.get().uri("/delai?codePostal={cp}", codePostal).retrieve().body(Reponse.class);
        return reponse.jours();
    }
}
