package com.nexossoftware.pruebanexos.service.implementation;

import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
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
