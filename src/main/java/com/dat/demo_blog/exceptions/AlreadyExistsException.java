package com.dat.demo_blog.exceptions;

public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(String s) {
    super(s);
  }
}
