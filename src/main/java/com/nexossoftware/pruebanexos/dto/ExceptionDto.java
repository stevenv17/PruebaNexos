package com.nexossoftware.pruebanexos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionDto {
  private String result = "ERROR";
  private String message;
  private Integer code;
}
