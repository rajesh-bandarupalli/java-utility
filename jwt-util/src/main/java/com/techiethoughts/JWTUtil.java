package com.techiethoughts;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/** @author Rajesh Bandarupalli */
public class JWTUtil {

  private static final String ISSUER = "techiethoughts.com";
  private static final String SECRET = "SECRET_PASSWORD";

  /**
   * Sample method to construct a JWT
   *
   * @param id can be any information, example userId
   * @param subject can be any information, example userName
   * @param ttlMillis token time to live in milli seconds, nothing but token expiration time.
   * @return a JWT.
   */
  public static String createJWT(String id, String subject, long ttlMillis) {

    // The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    // We will sign our JWT with our ApiKey secret
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    // Let's set the JWT Claims
    JwtBuilder builder =
        Jwts.builder()
            .setId(id)
            .setIssuedAt(now)
            .setSubject(subject)
            .setIssuer(ISSUER)
            .signWith(signatureAlgorithm, signingKey);

    // if it has been specified, let's add the expiration
    if (ttlMillis >= 0) {
      long expMillis = nowMillis + ttlMillis;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }

    // Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }

  // Sample method to validate and read the JWT
  private static Claims parseJWT(String jwt) {

    // This line will throw an exception if it is not a signed JWS (as expected)
    return Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
        .parseClaimsJws(jwt)
        .getBody();
  }

  public static String getUserName(String jwt) {
    Claims claims = parseJWT(jwt);
    return claims.getSubject();
  }
}
