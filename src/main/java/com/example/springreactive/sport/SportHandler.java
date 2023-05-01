package com.example.springreactive.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SportHandler {

  private final SportService sportService;

  public Mono<ServerResponse> createSport(ServerRequest request) {

    String sportName = request.pathVariable("sportName");

    return sportService.createSport(sportName)
      .flatMap(sport -> ServerResponse.ok().bodyValue(sport))
      .onErrorResume(SportAlreadyExistsException.class, e -> ServerResponse.status(HttpStatus.CONFLICT).bodyValue(e.getMessage()));
  }

  public Mono<ServerResponse> searchSports(ServerRequest request) {
    String query = request.queryParam("q").orElse("");
    return sportService.findSportByName(query)
      .flatMap(sport -> ServerResponse.ok().body(BodyInserters.fromValue(sport)));
  }
}



