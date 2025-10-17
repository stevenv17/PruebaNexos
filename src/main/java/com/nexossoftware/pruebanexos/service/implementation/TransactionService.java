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
import com.nexossoftware.pruebanexos.service.ITransactionService;
import com.nexossoftware.pruebanexos.utilities.CardStatusEnum;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import com.nexossoftware.pruebanexos.utilities.TransactionStatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

  private final CardRepository cardRepository;
  private final TransactionRepository transactionRepository;


  @Override
  @Transactional
  public TransactionDtoOut makePurchase(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardDtoIn.getCardId());
    if(cardEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Card not found");
    }

    CardEntity cardEntity = cardEntityOptional.get();
    purchaseValidations(cardEntity, cardDtoIn);

    Long newBalance = cardEntity.getBalance() - cardDtoIn.getPrice();
    cardEntity.setBalance(newBalance);
    cardRepository.save(cardEntity);

    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setCardNumber(cardEntity.getNumber());
    transactionEntity.setPrice(cardDtoIn.getPrice());
    transactionEntity.setStatus(TransactionStatusEnum.APPROVED.name());
    transactionEntity.setTransactionDate(new Date());
    TransactionEntity transactionEntitySaved = transactionRepository.save(transactionEntity);

    TransactionDtoOut transactionDtoOut = new TransactionDtoOut();
    transactionDtoOut.setResult(Constantes.MESSAGE_OK);
    transactionDtoOut.setMessage("Purchase made successfully");
    transactionDtoOut.setTransactionId(transactionEntitySaved.getId());
    return transactionDtoOut;
  }

  @Override
  @Transactional
  public MessageDtoOut purchaseAnnulation(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    Optional<TransactionEntity> transactionEntityOptional =
        transactionRepository.findByIdAndCardNumberApproved(cardDtoIn.getTransactionId(), cardDtoIn.getCardId());
    if(transactionEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Transaction APPROVED not found");
    }

    TransactionEntity transactionEntity = transactionEntityOptional.get();

    if(!canCancelTransaction(transactionEntity.getTransactionDate())) {
      throw new ErrorGeneralException("Transaction can only be annulled within 24 hours");
    }

    transactionEntity.setStatus(TransactionStatusEnum.CANCELED.name());
    transactionRepository.save(transactionEntity);

    CardEntity cardEntity = transactionEntity.getCard();
    long newBalance = cardEntity.getBalance() + transactionEntity.getPrice();
    cardEntity.setBalance(newBalance);
    cardRepository.save(cardEntity);


    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);
    messageDtoOut.setMessage("Transaction annulled");
    return messageDtoOut;
  }

  private void purchaseValidations(CardEntity cardEntity, CardDtoIn cardDtoIn) throws ErrorGeneralException {
    Date today = new Date();
    if(today.after(cardEntity.getExpirationDate())) {
      throw new ErrorGeneralException("The card has expired");
    }

    if(!cardEntity.getStatus().equals(CardStatusEnum.ACTIVE.name())) {
      throw new ErrorGeneralException(MessageFormat.format("Error: Card status is {0}", cardEntity.getStatus()));
    }

    if(cardDtoIn.getPrice() > cardEntity.getBalance()) {
      throw new ErrorGeneralException("insufficient founds");
    }
  }

  /**
   * Valida si esta en el rango de tiempo para cancelar transacción
   * @param transactionDate fecha transacción
   * @return true si han pasado menos de 24 horas
   */
  public boolean canCancelTransaction(Date transactionDate) {
    Date now = new Date();
    long diferenceMillisecons = now.getTime() - transactionDate.getTime();
    long hours = diferenceMillisecons / (1000 * 60 * 60);
    return hours < 24;
  }


}
