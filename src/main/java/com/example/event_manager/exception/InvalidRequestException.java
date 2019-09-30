package com.example.event_manager.exception;

public class InvalidRequestException extends RuntimeException {

  public InvalidRequestException(String msg) {
    super(msg);
  }

  public InvalidRequestException() {
  }

}
