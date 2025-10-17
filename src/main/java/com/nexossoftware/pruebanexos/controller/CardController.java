package com.nexossoftware.pruebanexos.controller;


import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.service.ICardService;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar tarjetas
 */
@Slf4j
@RestController
@RequestMapping(Constantes.PATH_CARD)
@RequiredArgsConstructor
public class CardController {

  private final ICardService iCardService;


  /**
   * Genera número de tarjeta
   * @param productId identificador del producto
   * @return datos de tarjeta
   * @throws ElementNotFoundException Excepción si no encuentra información
   */
  @GetMapping(value = "/{productId}/number", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CardDtoOut> generateCardNumber(@PathVariable String productId) throws ElementNotFoundException {
    return ResponseEntity.ok(iCardService.generateCardNumber(productId));
  }

}
