package com.silvioricardo.cotacaodolar.adapter.out.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseMessage {
  @JsonProperty("Codigo")
  private String codigo;

  @JsonProperty("Data")
  private String data;

  @JsonProperty("Mensagens")
  List<String> mensagens;

  @JsonProperty("Erro")
  boolean erro;
}
