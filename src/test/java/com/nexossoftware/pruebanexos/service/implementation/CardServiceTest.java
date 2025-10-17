package com.nexossoftware.pruebanexos.service.implementation;

import com.nexossoftware.pruebanexos.dto.CardBalanceDtoOut;
import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;
import com.nexossoftware.pruebanexos.jpa.entity.CardEntity;
import com.nexossoftware.pruebanexos.jpa.entity.ProductEntity;
import com.nexossoftware.pruebanexos.jpa.repository.CardRepository;
import com.nexossoftware.pruebanexos.jpa.repository.ProductRepository;
import com.nexossoftware.pruebanexos.mapper.CardMapper;
import com.nexossoftware.pruebanexos.utilities.CardStatusEnum;
import com.nexossoftware.pruebanexos.utilities.CardTypeEnum;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import com.nexossoftware.pruebanexos.utilities.ProductTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
  @InjectMocks private CardService cardService;
  @Mock private ProductRepository productRepository;
  @Mock private CardRepository cardRepository;
  @Mock private CardMapper cardMapper;


  @Test
  void generateCardNumberOk() throws ElementNotFoundException {
    String productId = "101010";

    ProductEntity productEntity = new ProductEntity();
    productEntity.setId(productId);
    productEntity.setPersonId(1L);
    productEntity.setProductTypeId(ProductTypeEnum.SAVING_ACCOUNT.name());

    CardDtoOut cardDtoOut = new CardDtoOut();
    cardDtoOut.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardDtoOut.setNumber("1010102222444356");
    cardDtoOut.setStatus(CardStatusEnum.INACTIVE.name());

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());

    when(productRepository.findById(anyString())).thenReturn(Optional.of(productEntity));
    when(cardRepository.save(any(CardEntity.class))).thenReturn(cardEntity);
    when(cardMapper.toDto(any(CardEntity.class))).thenReturn(cardDtoOut);

    CardDtoOut result = cardService.generateCardNumber(productId);
    assertEquals(CardStatusEnum.INACTIVE.name(), result.getStatus());
  }

  @Test
  void enrollCardOk() throws ElementNotFoundException {
    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    when(cardRepository.findById(anyString())).thenReturn(Optional.of(cardEntity));

    MessageDtoOut result = cardService.enrollCard(cardDtoIn);
    assertEquals(Constantes.MESSAGE_OK, result.getResult());
  }

  @Test
  void blockCardOk() throws ElementNotFoundException {
    String cardNumber = "2020201387691302";

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    when(cardRepository.findById(anyString())).thenReturn(Optional.of(cardEntity));

    MessageDtoOut result = cardService.blockCard(cardNumber);
    assertEquals(Constantes.MESSAGE_OK, result.getResult());
  }

  @Test
  void rechargeCardOk() throws ElementNotFoundException, ErrorGeneralException {
    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setBalance(20000L);

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardEntity.setBalance(0L);
    when(cardRepository.findById(anyString())).thenReturn(Optional.of(cardEntity));

    MessageDtoOut result = cardService.rechargeCard(cardDtoIn);
    assertEquals(Constantes.MESSAGE_OK, result.getResult());
  }

  @Test
  void obtainCardBalanceOk() throws ElementNotFoundException {
    String cardNumber = "2020201387691302";

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardEntity.setBalance(1000L);
    when(cardRepository.findById(anyString())).thenReturn(Optional.of(cardEntity));

    CardBalanceDtoOut result = cardService.obtainCardBalance(cardNumber);
    assertEquals(1000L, result.getBalance());
  }

}
