package com.example.springreactive;

import com.example.springreactive.sport.SportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartUpRunner implements CommandLineRunner {
  private final SportServiceImpl sportServiceImpl;

  @Override
  public void run(String... args) throws Exception {
    sportServiceImpl.fetchSportsAndPersist()
      .subscribe(new SportSubscriber(20));
  }

  public static class SportSubscriber<Sport> extends BaseSubscriber<Sport> {
    private final int batchSize;


    public SportSubscriber(int batchSize) {
      this.batchSize = batchSize;
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
      log.info("Subscription Done.");

      request(batchSize);
    }

    @Override
    protected void hookOnNext(Sport sport) {
      log.info("Received: " + sport);

      request(batchSize);
    }
  }
}
