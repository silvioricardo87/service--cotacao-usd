package com.silvioricardo.cotacaodolar.adapter.in.api;

import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
@SecurityRequirement(name = "X-API-KEY")
@Tag(name = "Cotação Dólar", description = "APIs para resgatar cotação do dólar")
public interface CotacaoDolarApi {
  @Operation(summary = "Consulta cotação ", description = "Consulta cotação do dólar na data especificada")
  @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))

  @GetMapping(value = "/cotacao", produces = "application/json")
  CotacaoDolar getCotacao(
      @RequestParam(required = false)
      @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "A data deve ser no formato MM-DD-YYYY")
      @Parameter(example = "09-25-2023", description = "Data da cotação no formato MM-DD-YYYY")
      String data);

  @Operation(summary = "Consulta cotações paginadas", description = "Consulta cotações do dólar paginadas")
  @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
  @GetMapping(value = "/cotacoes", produces = "application/json")
  Page<CotacaoDolar> getCotacoes(Pageable pageable);
}
