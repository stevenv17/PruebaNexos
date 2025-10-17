package com.nexossoftware.pruebanexos.service;

import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.dto.TransactionDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;

/** Interfaz con funcionalidades para gestionar transacciones */
public interface ITransactionService {

  TransactionDtoOut makePurchase(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException;

  MessageDtoOut purchaseAnnulation(CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException;
}
