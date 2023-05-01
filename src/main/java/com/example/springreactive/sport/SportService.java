package com.example.springreactive.sport;

import reactor.core.publisher.Mono;

public interface SportService {
  Mono<Sport> createSport(String sportName);

  Mono<Sport> findSportByName(String sportName);
}
