package com.silvioricardo.cotacaodolar.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.silvioricardo.cotacaodolar.adapter.in.exception.NotFoundException;
import com.silvioricardo.cotacaodolar.adapter.out.db.mongo.CotacoesDolarRepository;
import com.silvioricardo.cotacaodolar.adapter.out.feign.BacenClient;
import com.silvioricardo.cotacaodolar.domain.dto.bacen.BacenResponseDto;
import com.silvioricardo.cotacaodolar.domain.entities.CotacaoDolarEntity;
import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CotacaoDolarUseCase {
  private final RedisTemplate<String, CotacaoDolar> redisTemplate;
  private final CotacoesDolarRepository cotacoesDolarRepository;
  private final BacenClient bacenClient;
  private final ObjectMapper objectMapper;

  public CotacaoDolarUseCase(RedisTemplate<String, CotacaoDolar> redisTemplate,
      CotacoesDolarRepository cotacoesDolarRepository,
      BacenClient bacenClient,
      ObjectMapper objectMapper) {
    this.redisTemplate = redisTemplate;
    this.cotacoesDolarRepository = cotacoesDolarRepository;
    this.bacenClient = bacenClient;
    this.objectMapper = objectMapper;
    this.objectMapper.registerModule(new JavaTimeModule());
  }

  public CotacaoDolar getCotacao(String data) {
    Object cacheCotacaoDolar = redisTemplate.opsForValue().get(data);
    if (cacheCotacaoDolar != null) return objectMapper.convertValue(cacheCotacaoDolar, CotacaoDolar.class);

    Optional<CotacaoDolarEntity> cotacoesDolar = cotacoesDolarRepository.findByDataHoraCotacao(data);
    if (cotacoesDolar.isPresent()) {
      return atualizaCache(data, cotacoesDolar.get());
    }

    return buscaCotacaoBacen(data);
  }

  private CotacaoDolar buscaCotacaoBacen(String data) {
    BacenResponseDto bacenResponseDto = bacenClient.getCotacaoDolarDia(data);

    if(bacenResponseDto.getValue().isEmpty())
      throw new NotFoundException("Cotação não encontrada para a data informada");

    CotacaoDolar cotacaoDolar = CotacaoDolar.mapFromCotacaoDolarResponse(bacenResponseDto.getValue().get(0));
    CotacaoDolarEntity cotacaoDolarEntity = cotacoesDolarRepository.save(CotacaoDolarEntity.mapFromCotacaoDolar(cotacaoDolar));

    return atualizaCache(data, cotacaoDolarEntity);
  }

  private CotacaoDolar atualizaCache(String data, CotacaoDolarEntity cotacaoDolarEntity) {
    CotacaoDolar cotacaoDolar = CotacaoDolar.mapFromCotacaoDolarEntity(cotacaoDolarEntity);
    redisTemplate.opsForValue().set(data, cotacaoDolar);
    return cotacaoDolar;
  }

  public Page<CotacaoDolar> getCotacoes(Pageable pageable) {
    return cotacoesDolarRepository.findAll(pageable).map(CotacaoDolar::mapFromCotacaoDolarEntity);
  }
}
