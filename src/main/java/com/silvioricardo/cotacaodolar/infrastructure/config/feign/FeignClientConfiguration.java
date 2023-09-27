package com.silvioricardo.cotacaodolar.infrastructure.config.feign;

import com.silvioricardo.cotacaodolar.adapter.out.feign.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }
}
