package com.example.springreactive.sport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SportRouter {

  @Bean
  public RouterFunction<ServerResponse> sportRoutes(SportHandler handler) {
    return route(POST("/api/v1/sport/{sportName}")
      .and(accept(MediaType.APPLICATION_JSON)), handler::createSport)
      .andRoute(GET("/api/v1/sport")
        .and(queryParam("q", q -> true))
        .and(accept(MediaType.APPLICATION_JSON)), handler::searchSports);
  }
}