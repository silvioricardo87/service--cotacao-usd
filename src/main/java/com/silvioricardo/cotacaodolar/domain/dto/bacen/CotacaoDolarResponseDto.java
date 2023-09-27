package com.silvioricardo.cotacaodolar.domain.dto.bacen;

import lombok.Data;

@Data
public class CotacaoDolarResponseDto {
  private double cotacaoCompra;
  private double cotacaoVenda;
  private String dataHoraCotacao;
}
