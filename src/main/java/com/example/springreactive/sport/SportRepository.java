package com.example.springreactive.sport;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SportRepository extends ReactiveCrudRepository<Sport, Integer> {
  Mono<Sport> findByNameContainingIgnoreCase(String name);

}