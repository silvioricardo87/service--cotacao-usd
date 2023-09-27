package com.silvioricardo.cotacaodolar.adapter.in.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.silvioricardo.cotacaodolar.domain.model.CotacaoDolar;
import com.silvioricardo.cotacaodolar.usecase.CotacaoDolarUseCase;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(CotacaoDolarController.class)
class CotacaoDolarControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CotacaoDolarUseCase cotacaoDolarUseCase;

  private final String X_API_KEY_NAME = "X-API-KEY";
  private final String X_API_KEY_VALUE = "testes";

  @Test
  void deveRetornarNaoAutorizado() throws Exception {
    mockMvc.perform(get("/cotacao"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void deveObterCotacaoNaDataEspecificada() throws Exception {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    CotacaoDolar cotacaoDolar = new CotacaoDolar();
    cotacaoDolar.setId("651493919cb6136f6f2347d1");
    cotacaoDolar.setTimestampRequisicao(Instant.parse("2023-09-27T20:41:53.484939500Z"));
    cotacaoDolar.setDataHoraCotacao(LocalDateTime.parse("2023-09-25 13:10:42", dateTimeFormatter));
    cotacaoDolar.setCotacaoCompra(4.96);
    cotacaoDolar.setCotacaoVenda(4.9606);
    cotacaoDolar.setDataHoraRequisicao(LocalDateTime.parse("2023-09-27 17:41:53", dateTimeFormatter));

    when(cotacaoDolarUseCase.getCotacao(anyString())).thenReturn(cotacaoDolar);

    mockMvc.perform(get("/cotacao")
            .header(X_API_KEY_NAME, X_API_KEY_VALUE)
            .param("data", "09-25-2023"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("651493919cb6136f6f2347d1")))
        .andExpect(jsonPath("$.timestampRequisicao", is("2023-09-27T20:41:53.484939500Z")))
        .andExpect(jsonPath("$.dataHoraCotacao", is("2023-09-25 13:10:42")))
        .andExpect(jsonPath("$.cotacaoCompra", is(4.96)))
        .andExpect(jsonPath("$.cotacaoVenda", is(4.9606)))
        .andExpect(jsonPath("$.dataHoraRequisicao", is("2023-09-27 17:41:53")));
  }

  @Test
  void deveObterListaDeCotacoes() throws Exception {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    CotacaoDolar cotacaoDolar = new CotacaoDolar();
    cotacaoDolar.setId("651493919cb6136f6f2347d1");
    cotacaoDolar.setTimestampRequisicao(Instant.parse("2023-09-27T20:41:53.484939500Z"));
    cotacaoDolar.setDataHoraCotacao(LocalDateTime.parse("2023-09-25 13:10:42", dateTimeFormatter));
    cotacaoDolar.setCotacaoCompra(4.96);
    cotacaoDolar.setCotacaoVenda(4.9606);
    cotacaoDolar.setDataHoraRequisicao(LocalDateTime.parse("2023-09-27 17:41:53", dateTimeFormatter));
    Page<CotacaoDolar> cotacoesDolar = new PageImpl<>(List.of(cotacaoDolar));

    when(cotacaoDolarUseCase.getCotacoes(any())).thenReturn(cotacoesDolar);

    mockMvc.perform(get("/cotacoes")
            .header(X_API_KEY_NAME, X_API_KEY_VALUE)
            .param("data", "09-25-2023"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.[0].id", is("651493919cb6136f6f2347d1")))
        .andExpect(jsonPath("$.content.[0].timestampRequisicao", is("2023-09-27T20:41:53.484939500Z")))
        .andExpect(jsonPath("$.content.[0].dataHoraCotacao", is("2023-09-25 13:10:42")))
        .andExpect(jsonPath("$.content.[0].cotacaoCompra", is(4.96)))
        .andExpect(jsonPath("$.content.[0].cotacaoVenda", is(4.9606)))
        .andExpect(jsonPath("$.content.[0].dataHoraRequisicao", is("2023-09-27 17:41:53")));
  }
}