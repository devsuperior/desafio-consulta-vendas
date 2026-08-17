package com.devsuperior.dsmeta.controllers.handlers;

import com.devsuperior.dsmeta.dto.CustomError;
import com.devsuperior.dsmeta.services.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFound e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(),request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
