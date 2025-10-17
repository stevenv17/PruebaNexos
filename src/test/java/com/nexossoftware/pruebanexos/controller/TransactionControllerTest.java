package com.nexossoftware.pruebanexos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.dto.TransactionDtoOut;
import com.nexossoftware.pruebanexos.service.implementation.TransactionService;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TransactionService transactionService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void makePurchaseOk() throws Exception {
    TransactionDtoOut transactionDtoOut = new TransactionDtoOut();
    transactionDtoOut.setResult(Constantes.MESSAGE_OK);
    transactionDtoOut.setTransactionId(1L);

    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setPrice(200L);
    String json = objectMapper.writeValueAsString(cardDtoIn);

    when(transactionService.makePurchase(any(CardDtoIn.class))).thenReturn(transactionDtoOut);

    mockMvc.perform(post("/transaction/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(Constantes.MESSAGE_OK));
  }

  @Test
  void purchaseAnnulationOk() throws Exception {
    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);

    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setTransactionId(1L);
    String json = objectMapper.writeValueAsString(cardDtoIn);

    when(transactionService.purchaseAnnulation(any(CardDtoIn.class))).thenReturn(messageDtoOut);

    mockMvc.perform(post("/transaction/annulation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(Constantes.MESSAGE_OK));
  }

}
