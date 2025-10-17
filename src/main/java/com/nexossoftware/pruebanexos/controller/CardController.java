package com.nexossoftware.pruebanexos.controller;


import com.nexossoftware.pruebanexos.dto.CardBalanceDtoOut;
import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.CardDtoOut;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;
import com.nexossoftware.pruebanexos.service.ICardService;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  @GetMapping(value = Constantes.PATH_GENERATE_CARD, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CardDtoOut> generateCardNumber(@PathVariable String productId) throws ElementNotFoundException {
    return ResponseEntity.ok(iCardService.generateCardNumber(productId));
  }

  /**
   * Activar tarjeta
   * @param cardDtoIn datos de entrada
   * @return resultado
   * @throws ElementNotFoundException Excepción si no encuentra información
   */
  @PostMapping(value = Constantes.PATH_ENROLL_CARD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageDtoOut> enrollCard(@RequestBody CardDtoIn cardDtoIn) throws ElementNotFoundException {
    return ResponseEntity.ok(iCardService.enrollCard(cardDtoIn));
  }

  /**
   * Bloquear tarjeta
   * @param cardId número de tarjeta
   * @return resultado
   * @throws ElementNotFoundException Excepción si no encuentra información
   */
  @DeleteMapping(value = Constantes.PATH_BLOCK_CARD, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageDtoOut> blockCard(@PathVariable String cardId) throws ElementNotFoundException {
    return ResponseEntity.ok(iCardService.blockCard(cardId));
  }

  /**
   * Recargar tarjeta
   * @param cardDtoIn datos de entrada
   * @return resultado
   * @throws ElementNotFoundException Excepción si no encuentra información
   * @throws ErrorGeneralException Excepción si ocurre error
   */
  @PostMapping(value = Constantes.PATH_RECHARGE_CARD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageDtoOut> rechargeCard(@RequestBody CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    return ResponseEntity.ok(iCardService.rechargeCard(cardDtoIn));
  }

  /**
   * Obtener saldo de tarjeta
   * @param cardId número tarjeta
   * @return saldo de tarjeta
   * @throws ElementNotFoundException Excepción si no encuentra información
   */
  @GetMapping(value = Constantes.PATH_BALANCE_CARD, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CardBalanceDtoOut> obtainCardBalance(@PathVariable String cardId) throws ElementNotFoundException {
    return ResponseEntity.ok(iCardService.obtainCardBalance(cardId));
  }

}
