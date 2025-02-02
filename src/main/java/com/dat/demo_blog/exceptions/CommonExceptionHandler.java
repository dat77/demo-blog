package com.dat.demo_blog.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandler {

  private final ObjectMapper objectMapper;

  public CommonExceptionHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    ObjectNode errorNode = objectMapper.createObjectNode();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errorNode.put(error.getField(), error.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorNode);
  }

  @ExceptionHandler(IdNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<?> IdNotFoundExceptionHandler( IdNotFoundException e ){
    ObjectNode errorNode = objectMapper.createObjectNode();
    errorNode.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorNode);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ResponseEntity<?> AlreadyExistsExceptionHandler(AlreadyExistsException e){
    ObjectNode errorNode = objectMapper.createObjectNode();
    errorNode.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorNode);
  }

}
