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
import com.nexossoftware.pruebanexos.service.ICardService;
import com.nexossoftware.pruebanexos.utilities.CardStatusEnum;
import com.nexossoftware.pruebanexos.utilities.CardTypeEnum;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import com.nexossoftware.pruebanexos.utilities.ProductTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService implements ICardService {

  private final ProductRepository productRepository;
  private final CardRepository cardRepository;
  private final CardMapper cardMapper;

  @Override
  public CardDtoOut generateCardNumber(String productId) throws ElementNotFoundException {
    Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
    if(productEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Product not found");
    }

    String cardNumber = productId + generateRandomNumber();

    ProductEntity productEntity = productEntityOptional.get();
    String cardTypeId = productEntity.getProductTypeId().equals(ProductTypeEnum.SAVING_ACCOUNT.name()) ? CardTypeEnum.DEBIT.name() : CardTypeEnum.CREDIT.name();

    CardEntity cardEntity = new CardEntity();
    cardEntity.setNumber(cardNumber);
    cardEntity.setProductId(productId);
    cardEntity.setProduct(productEntity);
    cardEntity.setCardTypeId(cardTypeId);
    cardEntity.setIssueDate(new Date());
    cardEntity.setExpirationDate(calculateExpirationDate(cardEntity.getIssueDate()));
    cardEntity.setCurrency(Constantes.CURRENCY_COLLAR);
    cardEntity.setBalance(0L);
    cardEntity.setStatus(CardStatusEnum.INACTIVE.name());

    CardEntity cardEntitySaved = cardRepository.save(cardEntity);

    return cardMapper.toDto(cardEntitySaved);
  }

  @Override
  public MessageDtoOut enrollCard(CardDtoIn cardDtoIn) throws ElementNotFoundException {
    Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardDtoIn.getCardId());
    if(cardEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Card not found");
    }
    CardEntity cardEntity = cardEntityOptional.get();
    cardEntity.setStatus(CardStatusEnum.ACTIVE.name());
    cardRepository.save(cardEntity);

    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);
    messageDtoOut.setMessage("Card activated");
    return messageDtoOut;
  }

  @Override
  public MessageDtoOut blockCard(String cardId) throws ElementNotFoundException {
    Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);
    if(cardEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Card not found");
    }

    CardEntity cardEntity = cardEntityOptional.get();
    cardEntity.setStatus(CardStatusEnum.BLOCKED.name());
    cardRepository.save(cardEntity);

    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);
    messageDtoOut.setMessage("Card blocked");
    return messageDtoOut;
  }

  @Override
  public MessageDtoOut rechargeCard(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardDtoIn.getCardId());
    if(cardEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Card not found");
    }

    if(cardDtoIn.getBalance() < 0) {
      throw new ErrorGeneralException("Balance can't be less than 0");
    }

    CardEntity cardEntity = cardEntityOptional.get();
    Long newBalance = cardEntity.getBalance() + cardDtoIn.getBalance();
    cardEntity.setBalance(newBalance);
    cardRepository.save(cardEntity);

    MessageDtoOut messageDtoOut = new MessageDtoOut();
    messageDtoOut.setResult(Constantes.MESSAGE_OK);
    messageDtoOut.setMessage("Card recharged");
    return messageDtoOut;
  }

  @Override
  public CardBalanceDtoOut obtainCardBalance(String cardId) throws ElementNotFoundException {
    Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);
    if(cardEntityOptional.isEmpty()) {
      throw new ElementNotFoundException("Card not found");
    }
    CardEntity cardEntity = cardEntityOptional.get();

    CardBalanceDtoOut cardBalanceDtoOut = new CardBalanceDtoOut();
    cardBalanceDtoOut.setBalance(cardEntity.getBalance());
    return cardBalanceDtoOut;
  }

  /**
   * Genera número aleatorio de 10 cifras
   * @return número generado aleatoriamente
   */
  private String generateRandomNumber() {
    Random random = new Random();
    StringBuilder number = new StringBuilder();
    for (int j = 0; j < 10; j++) {
      number.append(random.nextInt(10)); // dígito entre 0 y 9
    }
    return number.toString();
  }

  /**
   * Calcula fecha de vencimiento de la tarjeta
   * @param issueDate fecha de expedición de la tarjeta
   * @return fecha de vencimiento de tarjeta
   */
  private Date calculateExpirationDate(Date issueDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(issueDate);
    calendar.add(Calendar.YEAR, 3);
    return calendar.getTime();
  }
}
