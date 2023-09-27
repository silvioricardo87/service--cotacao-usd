package com.silvioricardo.cotacaodolar.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.silvioricardo.cotacaodolar.domain.dto.bacen.CotacaoDolarResponseDto;
import com.silvioricardo.cotacaodolar.domain.entities.CotacaoDolarEntity;
import com.silvioricardo.cotacaodolar.util.DataDeserializer;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CotacaoDolar {
  private String id;

  private Instant timestampRequisicao;

  @JsonDeserialize(using = DataDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHoraCotacao;

  private double cotacaoCompra;

  private double cotacaoVenda;

  @JsonDeserialize(using = DataDeserializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHoraRequisicao;

  public static CotacaoDolar mapFromCotacaoDolarEntity(CotacaoDolarEntity cotacaoDolarEntity) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper.convertValue(cotacaoDolarEntity, new TypeReference<CotacaoDolar>(){});
  }

  public static CotacaoDolar mapFromCotacaoDolarResponse(CotacaoDolarResponseDto cotacaoDolarResponseDto) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    CotacaoDolar cotacaoDolar = objectMapper.convertValue(cotacaoDolarResponseDto, new TypeReference<CotacaoDolar>(){});
    cotacaoDolar.timestampRequisicao = Instant.now();
    cotacaoDolar.dataHoraRequisicao = LocalDateTime.now();

    return cotacaoDolar;
  }
}
