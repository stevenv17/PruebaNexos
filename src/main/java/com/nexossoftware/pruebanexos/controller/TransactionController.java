package com.nexossoftware.pruebanexos.controller;

import com.nexossoftware.pruebanexos.dto.CardDtoIn;
import com.nexossoftware.pruebanexos.dto.MessageDtoOut;
import com.nexossoftware.pruebanexos.dto.TransactionDtoOut;
import com.nexossoftware.pruebanexos.exception.ElementNotFoundException;
import com.nexossoftware.pruebanexos.exception.ErrorGeneralException;
import com.nexossoftware.pruebanexos.service.ITransactionService;
import com.nexossoftware.pruebanexos.utilities.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar transacciones
 */
@Slf4j
@RestController
@RequestMapping(Constantes.PATH_TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {

  private final ITransactionService iTransactionService;

  @PostMapping(value = Constantes.PATH_PURCHASE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionDtoOut> makePurchase(@RequestBody CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    return ResponseEntity.ok(iTransactionService.makePurchase(cardDtoIn));
  }

  @PostMapping(value = Constantes.PATH_PURCHASE_ANNULATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageDtoOut> purchaseAnnulation(@RequestBody CardDtoIn cardDtoIn) throws ElementNotFoundException, ErrorGeneralException {
    return ResponseEntity.ok(iTransactionService.purchaseAnnulation(cardDtoIn));
  }
}
