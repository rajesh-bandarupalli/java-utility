package com.techiethoughts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** @author Rajesh Bandarupalli */
public class JWTUtilTest {

  @Test
  public void createAndVerifyJWTToken() {

    String jwtToken = JWTUtil.createJWT("Techie123", "TechieThoughts", 60000);
    String userName = JWTUtil.getUserName(jwtToken);

    assertNotNull(jwtToken);
    assertEquals("TechieThoughts", userName);
  }
}
