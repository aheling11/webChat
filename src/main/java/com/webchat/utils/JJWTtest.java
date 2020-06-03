package com.webchat.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JJWTtest {
    public static void main(String[] args) {

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

        System.out.println(jws);

        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe");
    }
}
