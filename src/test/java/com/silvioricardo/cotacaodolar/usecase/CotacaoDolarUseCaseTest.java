package com.silvioricardo.cotacaodolar.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvioricardo.cotacaodolar.adapter.out.db.mongo.CotacoesDolarRepository;
import com.silvioricardo.cotacaodolar.adapter.out.feign.BacenClient;
import com.silvioricardo.cotacaodolar.domain.dto.bacen.BacenResponseDto;
import com.silvioricardo.cotacaodolar.domain.dto.bacen.CotacaoDolarResponseDto;
import com.silvioricardo.cotacaodolar.domain.entities.CotacaoDolarEntity;
import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CotacaoDolarUseCaseTest {

  @Mock
  private RedisTemplate<String, CotacaoDolar> redisTemplate;

  @Mock
  private CotacoesDolarRepository cotacoesDolarRepository;

  @Mock
  private BacenClient bacenClient;

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private ValueOperations<String, CotacaoDolar> valueOperations;

  @InjectMocks
  private CotacaoDolarUseCase cotacaoDolarUseCase;

  @BeforeEach
  public void setUp() {
    when(redisTemplate.opsForValue()).thenReturn(valueOperations);
  }

  @Test
  void pegaCotacaoDoCache() {
    CotacaoDolar cacheCotacaoDolar = new CotacaoDolar();
    cacheCotacaoDolar.setId("651485ee9208806c3eeca882");
    cacheCotacaoDolar.setCotacaoCompra(4.9125);
    cacheCotacaoDolar.setCotacaoVenda(4.9131);

    when(valueOperations.get("2023-09-22")).thenReturn(cacheCotacaoDolar);

    when(objectMapper.convertValue(cacheCotacaoDolar, CotacaoDolar.class)).thenReturn(cacheCotacaoDolar);

    ResponseEntity<CotacaoDolar> cotacaoDolar = cotacaoDolarUseCase.getCotacao("2023-09-22");

    assertEquals(cacheCotacaoDolar, cotacaoDolar.getBody());
  }

  @Test
  void pegaCotacaoBaseDados() {
    when(valueOperations.get("2023-09-27")).thenReturn(null);

    CotacaoDolarEntity cotacaoDolarEntity = new CotacaoDolarEntity();
    cotacaoDolarEntity.setId("651485ee9208806c3eeca882");
    cotacaoDolarEntity.setCotacaoCompra(4.9125);
    cotacaoDolarEntity.setCotacaoVenda(4.9131);

    when(cotacoesDolarRepository.findByDataHoraCotacao("2023-09-27")).thenReturn(Optional.of(cotacaoDolarEntity));

    CotacaoDolar mockCotacaoDolar = new CotacaoDolar();
    mockCotacaoDolar.setId("651485ee9208806c3eeca882");
    mockCotacaoDolar.setCotacaoCompra(4.9125);
    mockCotacaoDolar.setCotacaoVenda(4.9131);

    ResponseEntity<CotacaoDolar> cotacaoDolar = cotacaoDolarUseCase.getCotacao("2023-09-27");

    assertEquals("651485ee9208806c3eeca882", Objects.requireNonNull(cotacaoDolar.getBody()).getId());
    assertEquals(4.9125, cotacaoDolar.getBody().getCotacaoCompra());
    assertEquals(4.9131, cotacaoDolar.getBody().getCotacaoVenda());
  }

  @Test
  void pegaCotacaoBacen() {
    when(valueOperations.get("2023-09-27")).thenReturn(null);
    when(cotacoesDolarRepository.findByDataHoraCotacao("2023-09-27")).thenReturn(Optional.empty());

    CotacaoDolarResponseDto cotacaoDolarResponseDto = new CotacaoDolarResponseDto();
    cotacaoDolarResponseDto.setDataHoraCotacao("2023-09-27 19:17:00");
    cotacaoDolarResponseDto.setCotacaoCompra(4.9125);
    cotacaoDolarResponseDto.setCotacaoVenda(4.9131);

    BacenResponseDto bacenResponseDto = new BacenResponseDto();
    bacenResponseDto.setValue(List.of(cotacaoDolarResponseDto));
    when(bacenClient.getCotacaoDolarDia("2023-09-27")).thenReturn(bacenResponseDto);

    CotacaoDolarEntity cotacaoDolarEntity = new CotacaoDolarEntity();
    cotacaoDolarEntity.setId("651485ee9208806c3eeca882");
    cotacaoDolarEntity.setCotacaoCompra(4.9125);
    cotacaoDolarEntity.setCotacaoVenda(4.9131);
    when(cotacoesDolarRepository.save(any())).thenReturn(cotacaoDolarEntity);

    ResponseEntity<CotacaoDolar> cotacaoDolar = cotacaoDolarUseCase.getCotacao("2023-09-27");

    assertEquals("651485ee9208806c3eeca882", Objects.requireNonNull(cotacaoDolar.getBody()).getId());
    assertEquals(4.9125, cotacaoDolar.getBody().getCotacaoCompra());
    assertEquals(4.9131, cotacaoDolar.getBody().getCotacaoVenda());
  }
}