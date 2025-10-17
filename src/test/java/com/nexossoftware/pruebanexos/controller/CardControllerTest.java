package com.nexossoftware.pruebanexos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexossoftware.pruebanexos.dto.CardBalanceDtoOut;
import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.service.implementation.CardService;
import com.nexossoftware.pruebanexos.utilities.CardStatusEnum;
import com.nexossoftware.pruebanexos.utilities.CardTypeEnum;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CardService cardService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void generateCardNumberOk() throws Exception {
    String cardNumber = "1010102222444356";

    CardDtoOut cardDtoOut = new CardDtoOut();
    cardDtoOut.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardDtoOut.setNumber(cardNumber);
    cardDtoOut.setStatus(CardStatusEnum.INACTIVE.name());

    when(cardService.generateCardNumber(anyString())).thenReturn(cardDtoOut);

    mockMvc.perform(get("/card/101010/number")).andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(cardNumber));
  }

  @Test
  void enrollCardOk() throws Exception {
    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);

    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    String json = objectMapper.writeValueAsString(cardDtoIn);

    when(cardService.enrollCard(any(CardDtoIn.class))).thenReturn(messageDtoOut);

    mockMvc.perform(post("/card/enroll")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(Constantes.MESSAGE_OK));
  }

  @Test
  void blockCardOk() throws Exception {
    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);

    when(cardService.blockCard(anyString())).thenReturn(messageDtoOut);

    mockMvc.perform(delete("/card/2020201387691302")).andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(Constantes.MESSAGE_OK));
  }

  @Test
  void rechargeCardOk() throws Exception {
    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);

    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setBalance(20000L);
    String json = objectMapper.writeValueAsString(cardDtoIn);

    when(cardService.rechargeCard(any(CardDtoIn.class))).thenReturn(messageDtoOut);

    mockMvc.perform(post("/card/balance")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(Constantes.MESSAGE_OK));
  }

  @Test
  void obtainCardBalanceOk() throws Exception {
    long balance = 30000L;
    CardBalanceDtoOut cardBalanceDtoOut = new CardBalanceDtoOut();
    cardBalanceDtoOut.setBalance(balance);

    when(cardService.obtainCardBalance(anyString())).thenReturn(cardBalanceDtoOut);

    mockMvc.perform(get("/card/balance/2020201387691302")).andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(balance));
  }

}
