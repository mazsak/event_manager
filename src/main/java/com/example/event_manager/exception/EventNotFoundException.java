package com.example.event_manager.exception;


public class EventNotFoundException extends RuntimeException {

  public EventNotFoundException(final String msg) {
    super(msg);
  }

  public EventNotFoundException() {
  }

}
