package com.example.springreactive.sport;

public class SportAlreadyExistsException extends RuntimeException {
  private final String message;

  public SportAlreadyExistsException() {
    this.message = "";
  }

  public SportAlreadyExistsException(String message) {
    super(message);
    this.message = message;
  }
}
