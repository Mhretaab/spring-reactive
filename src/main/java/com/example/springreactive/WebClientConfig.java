package com.nufactor.gpo.common.config.web;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

  //TODO: externalize as properties

  private final int maxConnections = 20;
  private final long acquireTimeoutMillis = 5000;
  private final int connectionTimeoutMillis = 5000;
  private final long responseTimeoutMillis = 5000;
  private final int readTimeoutMillis = 10000;
  private final int writeTimeoutMillis = 5000;

  @Bean
  public WebClient.Builder webClientBuilder() {

    ConnectionProvider connProvider = ConnectionProvider
      .builder("webclient-conn-pool")
      .maxConnections(maxConnections)
      .pendingAcquireTimeout(Duration.ofMillis(acquireTimeoutMillis))
      .build();

    HttpClient httpClient = HttpClient.create(connProvider)
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis)
      .responseTimeout(Duration.ofMillis(responseTimeoutMillis))
      .doOnConnected(conn ->
        conn
          .addHandlerLast(new ReadTimeoutHandler(readTimeoutMillis, TimeUnit.MILLISECONDS))
          .addHandlerLast(new WriteTimeoutHandler(writeTimeoutMillis, TimeUnit.MILLISECONDS))
      );

    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
      .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
      .build();

    return WebClient.builder()
      .exchangeStrategies(strategies)
      .clientConnector(new ReactorClientHttpConnector(httpClient));
  }
}
