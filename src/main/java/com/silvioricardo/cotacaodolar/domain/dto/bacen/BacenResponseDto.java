package com.silvioricardo.cotacaodolar.domain.dto.bacen;

import java.util.List;
import lombok.Data;

@Data
public class BacenResponseDto {
  private List<CotacaoDolarResponseDto> value;
}
