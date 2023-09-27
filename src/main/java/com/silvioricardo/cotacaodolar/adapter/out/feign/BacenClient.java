package com.silvioricardo.cotacaodolar.adapter.out.feign;

import com.silvioricardo.cotacaodolar.domain.dto.bacen.BacenResponseDto;
import com.silvioricardo.cotacaodolar.infrastructure.config.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "BacenClientAPI",
    url="${bacen.url}",
  configuration = FeignClientConfiguration.class)
@Service
public interface BacenClient {
  @GetMapping("/CotacaoDolarDia(dataCotacao=@dataCotacao)?%40dataCotacao=%27{data}%27&%24format=json")
  BacenResponseDto getCotacaoDolarDia(@PathVariable String data);
}
