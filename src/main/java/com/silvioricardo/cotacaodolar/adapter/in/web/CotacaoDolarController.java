package com.silvioricardo.cotacaodolar.adapter.in.web;

import com.silvioricardo.cotacaodolar.adapter.in.api.CotacaoDolarApi;
import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import com.silvioricardo.cotacaodolar.usecase.CotacaoDolarUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CotacaoDolarController implements CotacaoDolarApi {
  private final CotacaoDolarUseCase cotacaoDolarUseCase;

  public CotacaoDolarController(CotacaoDolarUseCase cotacaoDolarUseCase) {
    this.cotacaoDolarUseCase = cotacaoDolarUseCase;
  }

  @Override
  public CotacaoDolar getCotacao(String data) {
    return cotacaoDolarUseCase.getCotacao(data);
  }

  @Override
  public Page<CotacaoDolar> getCotacoes(@PageableDefault(size = 20) Pageable pageable) {
    return cotacaoDolarUseCase.getCotacoes(pageable);
  }
}
