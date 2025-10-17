package com.nexossoftware.pruebanexos.exception;

import com.nexossoftware.pruebanexos.dto.ExceptionDto;
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
    log.error("Exception: ", ex);
    ExceptionDto exceptionDto = ExceptionDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Unexpected error")
        .build();

    return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ErrorGeneralException.class)
  public ResponseEntity<Object> manejarErrorGeneral(ErrorGeneralException ex) {
    log.error("Exception general: ", ex);
    ExceptionDto exceptionDto = ExceptionDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ElementNotFoundException.class)
  public ResponseEntity<Object> manejarElementoNoEncontrado(ElementNotFoundException ex) {
    log.error("Exception not found: ", ex);
    ExceptionDto exceptionDto = ExceptionDto.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
  }

}

