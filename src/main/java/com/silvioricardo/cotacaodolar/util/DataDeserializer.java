package com.silvioricardo.cotacaodolar.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataDeserializer extends JsonDeserializer<LocalDateTime> {
  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return LocalDateTime.parse(
        p.getValueAsString().substring(0, 19),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    );
  }
}