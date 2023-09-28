package com.silvioricardo.cotacaodolar.adapter.in.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
@SecurityRequirement(name = "X-API-KEY")
@Tag(name = "Cotação Dólar", description = "APIs para resgatar cotação do dólar")
public interface CotacaoDolarApi {
  @Operation(summary = "Consulta cotação", description = "Consulta cotação do dólar na data especificada ou retorna a lista, senão for especificada.")
  @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
  @GetMapping(value = "/cotacao", produces = "application/json")
  ResponseEntity<Object> getCotacao(
      @RequestParam(required = false)
      @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "A data deve ser no formato MM-DD-YYYY")
      @Parameter(example = "09-25-2023", description = "Data da cotação no formato MM-DD-YYYY")
      String data,

      @Parameter(hidden = true)
      @PageableDefault(size = 20)
      Pageable pageable,

      @RequestParam(required = false)
      @Parameter(name = "page", example = "0", description = "Número da página")
      Integer page,

      @RequestParam(required = false)
      @Parameter(name = "size", example = "20", description = "Tamanho da página")
      Integer size,

      @RequestParam(required = false)
      @Parameter(name = "sort", example = "dataHoraCotacao,desc", description = "Ordenação")
      String sort
      );
}
