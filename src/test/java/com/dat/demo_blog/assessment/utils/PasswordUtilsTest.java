package com.dat.demo_blog.assessment.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dat.demo_blog.utils.PasswordUtils;
import org.junit.jupiter.api.Test;

public class PasswordUtilsTest {

  @Test
  public void passwordTest() {
    String pwd1 = "Wild1";
    String pwd3 = "Wild2";
    String pwd1Hash = PasswordUtils.hashPassword(pwd1);
    String pwd2Hash = PasswordUtils.hashPassword(pwd1);
    String pwd3Hash = PasswordUtils.hashPassword(pwd3);

    assertNotEquals(pwd1Hash, pwd2Hash);
    assertNotEquals(pwd1Hash, pwd3Hash);
    assertTrue(PasswordUtils.checkPassword(pwd1, pwd1Hash));
    assertTrue(PasswordUtils.checkPassword(pwd1, pwd2Hash));
    assertTrue(PasswordUtils.checkPassword(pwd3, pwd3Hash));
    assertFalse(PasswordUtils.checkPassword(pwd1, pwd3Hash));
  }

}
