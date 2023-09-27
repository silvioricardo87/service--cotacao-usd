package com.silvioricardo.cotacaodolar.infrastructure.config.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CotacaoAuthorizationFilter extends OncePerRequestFilter {
  private static final String API_KEY_NAME = "X-API-KEY";

  @Value("${cotacaodolar.api-key}")
  private String cotacaoDolar;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  protected static final String[] listaPermtida = {
      "/actuator",
      "/actuator/*/**",
      "/api-docs/**",
      "/v3/api-docs/**",
      "/swagger-ui.html",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/webjars/**"
  };

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    boolean isPermitted = Arrays.stream(listaPermtida).anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

    if(isPermitted) {
      filterChain.doFilter(request, response);
      return;
    }

    if(request.getHeader(API_KEY_NAME) == null) {
      response.sendError(401, "Chave de API inválida, verifique os dados e tente novamente");
      return;
    }

    if(!request.getHeader(API_KEY_NAME).equals(cotacaoDolar)) {
      response.sendError(401, "Chave de API inválida, verifique os dados e tente novamente");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
