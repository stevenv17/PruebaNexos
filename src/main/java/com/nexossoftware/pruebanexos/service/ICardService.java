package com.nexossoftware.pruebanexos.service;

import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;

/** Interfaz con funcionalidades para gestionar tarjetas */
public interface ICardService {

  CardDtoOut generateCardNumber(String productId) throws ElementNotFoundException;
}
