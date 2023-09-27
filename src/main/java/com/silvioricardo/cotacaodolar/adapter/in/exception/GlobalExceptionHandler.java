package com.silvioricardo.cotacaodolar.adapter.in.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvioricardo.cotacaodolar.adapter.out.feign.ErrorResponseMessage;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @JsonFormat
  public String genericException(final Exception exception) {
    log.error(exception.getMessage(), exception);
    return exception.getMessage();
  }

  @ExceptionHandler(InternalErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  void internalErrorException(final InternalErrorException internalErrorException) {
    log.error(internalErrorException.getMessage(), internalErrorException);
  }

  @ExceptionHandler(BadGatewayException.class)
  @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
  void badGatewayException(final BadGatewayException badGatewayException) {
    log.error(badGatewayException.getMessage(), badGatewayException);
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
  void serviceUnavailableException(final ServiceUnavailableException serviceUnavailableException) {
    log.error(serviceUnavailableException.getMessage(), serviceUnavailableException);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @JsonFormat
  public ErrorResponseMessage badRequestException(final BadRequestException badRequestException) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(badRequestException.getMessage(), ErrorResponseMessage.class);
  }

  @ExceptionHandler(NotAuthorizedException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  void notAuthorizedException(final NotAuthorizedException notAuthorizedException) {
    log.error(notAuthorizedException.getMessage(), notAuthorizedException);
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  void forbiddenException(final ForbiddenException forbiddenException) {
    log.error(forbiddenException.getMessage(), forbiddenException);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  void notFoundException(final NotFoundException notFoundException) {
    log.error(notFoundException.getMessage(), notFoundException);
  }

  @ExceptionHandler(RetryableException.class)
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
  void retryableException(final RetryableException retryableException) {
    log.error(retryableException.getMessage());
  }
}
