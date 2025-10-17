package com.nexossoftware.pruebanexos.service;

import com.nexossoftware.pruebanexos.dto.CardBalanceDtoOut;
import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;

/** Interfaz con funcionalidades para gestionar tarjetas */
public interface ICardService {

  CardDtoOut generateCardNumber(String productId) throws ElementNotFoundException;

  MessageDtoOut enrollCard(CardDtoIn cardDtoIn) throws ElementNotFoundException;

  MessageDtoOut blockCard(String cardId) throws ElementNotFoundException;

  MessageDtoOut rechargeCard(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException;

  CardBalanceDtoOut obtainCardBalance(String cardId) throws ElementNotFoundException;
}
