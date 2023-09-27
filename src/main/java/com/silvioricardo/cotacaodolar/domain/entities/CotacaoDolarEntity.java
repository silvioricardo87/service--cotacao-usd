package com.silvioricardo.cotacaodolar.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cotacoes")
public class CotacaoDolarEntity {
  @Id
  private String id;

  private Instant timestampRequisicao;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHoraCotacao;

  private double cotacaoCompra;

  private double cotacaoVenda;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHoraRequisicao;



  public CotacaoDolarEntity() {
    this.timestampRequisicao = Instant.now();
    this.dataHoraRequisicao = LocalDateTime.now();
  }

  public static CotacaoDolarEntity mapFromCotacaoDolar(CotacaoDolar cotacaoDolar) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper.convertValue(cotacaoDolar, CotacaoDolarEntity.class);
  }
}
