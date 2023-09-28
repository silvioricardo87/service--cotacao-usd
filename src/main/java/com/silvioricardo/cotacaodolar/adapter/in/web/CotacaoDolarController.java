package com.silvioricardo.cotacaodolar.adapter.in.web;

import com.silvioricardo.cotacaodolar.adapter.in.api.CotacaoDolarApi;
import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import com.silvioricardo.cotacaodolar.usecase.CotacaoDolarUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CotacaoDolarController implements CotacaoDolarApi {
  private final CotacaoDolarUseCase cotacaoDolarUseCase;

  public CotacaoDolarController(CotacaoDolarUseCase cotacaoDolarUseCase) {
    this.cotacaoDolarUseCase = cotacaoDolarUseCase;
  }

  @Override
  public ResponseEntity<Object> getCotacao(String data, Pageable pageable, Integer page, Integer size, String sort){
    if(data == null) {
      Page<CotacaoDolar> cotacoes = cotacaoDolarUseCase.getCotacoes(pageable);
      return ResponseEntity.ok().body(cotacoes.getContent());

    }

    return ResponseEntity.ok().body(cotacaoDolarUseCase.getCotacao(data).getBody());
  }
}
