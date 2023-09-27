package com.silvioricardo.cotacaodolar.adapter.in.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}