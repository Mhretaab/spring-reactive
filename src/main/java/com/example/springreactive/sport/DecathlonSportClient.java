package com.example.springreactive.sport;

import reactor.core.publisher.Flux;

public interface DecathlonSportClient {
  Flux<Sport> fetchSports();
}
