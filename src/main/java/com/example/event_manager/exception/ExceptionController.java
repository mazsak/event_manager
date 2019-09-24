package com.example.event_manager.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = EventNotFoundException.class)
  public String handleError(final WebRequest request, final Model model,
      final Exception exception) {
    model.addAttribute("exception", "Can't find element what you looking for");
    StringBuffer urlBuffer = ((ServletWebRequest) request).getRequest().getRequestURL();
    model.addAttribute("url", urlBuffer.toString());
    return "error";
  }

  @ExceptionHandler(value = InvalidRequestException.class)
  public String invalidRequest(WebRequest request, Model model, Exception exception) {
    model.addAttribute("exception", "Bad request");
    StringBuffer urlBuffer = ((ServletWebRequest) request).getRequest().getRequestURL();
    model.addAttribute("url", urlBuffer.toString());
    return "error";
  }
}
