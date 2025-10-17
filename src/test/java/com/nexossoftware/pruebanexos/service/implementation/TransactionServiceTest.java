package com.nexossoftware.pruebanexos.service.implementation;

import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.dto.TransactionDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;
import com.nexossoftware.pruebanexos.jpa.entity.CardEntity;
import com.nexossoftware.pruebanexos.jpa.entity.TransactionEntity;
import com.nexossoftware.pruebanexos.jpa.repository.CardRepository;
import com.nexossoftware.pruebanexos.jpa.repository.TransactionRepository;
import com.nexossoftware.pruebanexos.utilities.CardStatusEnum;
import com.nexossoftware.pruebanexos.utilities.CardTypeEnum;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import com.nexossoftware.pruebanexos.utilities.TransactionStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
  @InjectMocks private TransactionService transactionService;
  @Mock private CardRepository cardRepository;
  @Mock private TransactionRepository transactionRepository;

  @Test
  void makePurchaseOk() throws ElementNotFoundException, ErrorGeneralException {
    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setPrice(200L);

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date tomorrow = calendar.getTime();

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.ACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardEntity.setBalance(1000L);
    cardEntity.setExpirationDate(tomorrow);

    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setId(1L);
    transactionEntity.setPrice(cardDtoIn.getPrice());
    transactionEntity.setCardNumber(cardDtoIn.getCardId());
    transactionEntity.setStatus(TransactionStatusEnum.APPROVED.name());

    when(cardRepository.findById(anyString())).thenReturn(Optional.of(cardEntity));
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);


    TransactionDtoOut result = transactionService.makePurchase(cardDtoIn);
    assertEquals(Constantes.MESSAGE_OK, result.getResult());
  }

  @Test
  void purchaseAnnulationOk() throws ElementNotFoundException, ErrorGeneralException {
    CardDtoIn cardDtoIn = new CardDtoIn();
    cardDtoIn.setCardId("2020201387691302");
    cardDtoIn.setPrice(200L);
    cardDtoIn.setTransactionId(1L);

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date tomorrow = calendar.getTime();

    CardEntity cardEntity = new CardEntity();
    cardEntity.setStatus(CardStatusEnum.ACTIVE.name());
    cardEntity.setCardTypeId(CardTypeEnum.DEBIT.name());
    cardEntity.setBalance(1000L);
    cardEntity.setExpirationDate(tomorrow);

    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setId(1L);
    transactionEntity.setPrice(cardDtoIn.getPrice());
    transactionEntity.setCardNumber(cardDtoIn.getCardId());
    transactionEntity.setStatus(TransactionStatusEnum.APPROVED.name());
    transactionEntity.setCard(cardEntity);
    transactionEntity.setTransactionDate(new Date());

    when(transactionRepository.findByIdAndCardNumberApproved(anyLong(), anyString())).thenReturn(Optional.of(transactionEntity));

    MessageDtoOut result = transactionService.purchaseAnnulation(cardDtoIn);
    assertEquals(Constantes.MESSAGE_OK, result.getResult());
  }

}
