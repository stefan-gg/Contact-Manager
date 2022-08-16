package com.ing.contactmanager.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ejb.NoSuchEntityException;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleNoSuchEntityException(NoSuchEntityException ex) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handleSQLException(PSQLException psqlException) {

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleSQLException(IllegalArgumentException exception) {

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {

        return ResponseEntity.badRequest().build();
    }
}
