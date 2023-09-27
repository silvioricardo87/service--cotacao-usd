package com.silvioricardo.cotacaodolar.adapter.in.exception;

public class NotAuthorizedException extends RuntimeException {
  public NotAuthorizedException(String message) {
    super(message);
  }
}