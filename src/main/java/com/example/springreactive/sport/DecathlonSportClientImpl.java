package com.example.springreactive.sport;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class DecathlonSportClientImpl implements DecathlonSportClient {

  private final WebClient webClient;

  @Autowired
  public DecathlonSportClientImpl(final WebClient.Builder WebClientBuilder) {
    webClient = WebClientBuilder
      .baseUrl("https://sports.api.decathlon.com")
      .build();
  }

  @Override
  public Flux<Sport> fetchSports() {
    return webClient.get().uri("/sports")
      .retrieve()
      .bodyToMono(JsonNode.class)
      .flatMapMany(sportsNode -> Flux.fromIterable(() -> sportsNode.get("data").elements()))
      .log()
      .map(jsonNode -> {
        String name = jsonNode.get("attributes").get("name").asText();
        return new Sport(name);
      });
  }
}
