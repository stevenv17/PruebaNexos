package com.nexossoftware.pruebanexos.exception;

import com.nexossoftware.pruebanexos.dto.ExcepcionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ManejadorGlobalExcepciones {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> manejarErrorGeneral(Exception ex) {
    log.error("Excepción: ", ex);
    ExcepcionDto excepcionDto = ExcepcionDto.builder()
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje("Ha ocurrido un error inesperado")
        .build();

    return new ResponseEntity<>(excepcionDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ErrorGeneralException.class)
  public ResponseEntity<Object> manejarErrorGeneral(ErrorGeneralException ex) {
    log.error("Excepción general: ", ex);
    ExcepcionDto excepcionDto = ExcepcionDto.builder()
        .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .mensaje(ex.getMessage())
        .build();

    return new ResponseEntity<>(excepcionDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ElementoNoEncontradoException.class)
  public ResponseEntity<Object> manejarElementoNoEncontrado(ElementoNoEncontradoException ex) {
    log.error("Excepción not found: ", ex);
    ExcepcionDto excepcionDto = ExcepcionDto.builder()
        .codigo(HttpStatus.NOT_FOUND.value())
        .mensaje(ex.getMessage())
        .build();

    return new ResponseEntity<>(excepcionDto, HttpStatus.NOT_FOUND);
  }

}

