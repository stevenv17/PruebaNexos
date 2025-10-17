package com.nexossoftware.pruebanexos.utilities;

public final class Constantes {

  /**
   * Constructor
   */
  private Constantes() {}

  /** Dolar */
  public static final String CURRENCY_COLLAR = "USD";

  /** Path tarjeta */
  public static final String PATH_CARD = "/card";
  /** Path transacción */
  public static final String PATH_TRANSACTION = "/transaction";
  /** Path */
  public static final String PATH_GENERATE_CARD = "/{productId}/number";
  /** Path */
  public static final String PATH_ENROLL_CARD = "/enroll";
  /** Path */
  public static final String PATH_BLOCK_CARD = "/{cardId}";
  /** Path */
  public static final String PATH_RECHARGE_CARD = "/balance";
  /** Path */
  public static final String PATH_BALANCE_CARD = "/balance/{cardId}";
  /** Path */
  public static final String PATH_PURCHASE = "/purchase";
  /** Path */
  public static final String PATH_PURCHASE_ANNULATION = "/annulation";
  /** Resultado respuesta */
  public static final String MESSAGE_OK = "OK";
}

