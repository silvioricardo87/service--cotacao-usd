package com.silvioricardo.cotacaodolar.infrastructure.config.security;

import com.silvioricardo.cotacaodolar.infrastructure.config.filter.CotacaoAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CotacaoAuthorizationFilter cotacaoAuthorizationFilter;

  protected static final String[] listaPermtida = {
      "/login",
      "/actuator",
      "/actuator/*/**",
      "/api-docs/**",
      "/v3/api-docs/**",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/webjars/**"
  };

  public SecurityConfig(CotacaoAuthorizationFilter cotacaoAuthorizationFilter) {
    this.cotacaoAuthorizationFilter = cotacaoAuthorizationFilter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()
        .addFilterBefore(cotacaoAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers(listaPermtida).permitAll();
  }
}
