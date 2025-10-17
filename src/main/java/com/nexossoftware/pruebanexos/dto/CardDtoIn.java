package com.nexossoftware.pruebanexos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDtoIn {
  private String cardId;
  private Long balance;
  private Long price;
  private Long transactionId;
}
