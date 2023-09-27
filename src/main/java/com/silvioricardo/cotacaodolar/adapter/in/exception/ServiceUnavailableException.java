package com.silvioricardo.cotacaodolar.adapter.in.exception;

public class ServiceUnavailableException extends RuntimeException {
  public ServiceUnavailableException(String message) {
    super(message);
  }
}