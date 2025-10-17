package com.nexossoftware.pruebanexos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcepcionDto {
  private String resultado = "ERROR";
  private String mensaje;
  private Integer codigo;
}
