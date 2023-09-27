package com.silvioricardo.cotacaodolar.adapter.out.feign;

import com.silvioricardo.cotacaodolar.adapter.in.exception.BadGatewayException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.BadRequestException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.ForbiddenException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.InternalErrorException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.NotAuthorizedException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.NotFoundException;
import com.silvioricardo.cotacaodolar.adapter.in.exception.ServiceUnavailableException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String methodKey, Response response) {
    String responseBody = getResponseBody(response);

    return switch (response.status()) {
      case 400 -> "Cliente já existe".equals(responseBody) ? null : new BadRequestException(responseBody);
      case 401 -> new NotAuthorizedException("Erro de autenticação: usuário ou senha inválidos");
      case 403 -> new ForbiddenException("Erro de autorização: usuário não autorizado");
      case 404 -> new NotFoundException("Recurso não encontrado");
      case 500 -> new InternalErrorException("Erro interno do servidor");
      case 502 -> new BadGatewayException("Erro de gateway: serviço indisponível");
      case 503 -> new ServiceUnavailableException("Erro de serviço indisponível");
      default -> new InternalErrorException("Erro desconhecido");
    };
  }

  private String getResponseBody(Response response) {
    if (response.body() == null) {
      return "";
    }

    try (var inputStream = response.body().asInputStream()) {
      return new String(Util.toByteArray(inputStream), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new InternalErrorException("Erro ao processar a requisição");
    }
  }
}
