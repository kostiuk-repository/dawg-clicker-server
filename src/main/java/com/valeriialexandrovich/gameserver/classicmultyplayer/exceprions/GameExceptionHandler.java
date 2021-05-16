package com.valeriialexandrovich.gameserver.classicmultyplayer.exceprions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(value = StepNotFoundException.class)
    public ResponseEntity<Object> handleStepNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>("Opponent step wasn't found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
