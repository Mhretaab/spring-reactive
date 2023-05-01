package com.example.springreactive.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SportServiceImpl implements SportService {

  private final SportRepository sportRepository;
  private final DecathlonSportClient decathlonSportClient;


  public Flux<Sport> fetchSportsAndPersist() {
    return decathlonSportClient.fetchSports()
      .flatMap(sportRepository::save)
      .onErrorContinue((throwable, o) -> {
        log.error("Error: " + throwable.getMessage());
        if (o != null) {
          log.info("Data: " + o);
        }
      });
  }

  @Override
  public Mono<Sport> createSport(String sportName) {

    return sportRepository.findByNameContainingIgnoreCase(sportName)
      .flatMap(existingSport -> Mono.<Sport>error(new SportAlreadyExistsException("Sport already exists")))
      .switchIfEmpty(Mono.defer(() -> sportRepository.save(new Sport(sportName))));
  }

  @Override
  public Mono<Sport> findSportByName(String sportName) {
    return sportRepository.findByNameContainingIgnoreCase(sportName);
  }
}
