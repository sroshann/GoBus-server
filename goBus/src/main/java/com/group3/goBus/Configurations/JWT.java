package com.group3.goBus.Configurations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWT {

    private static final Key token = Keys.secretKeyFor( SignatureAlgorithm.HS256 );
    public static String generateToken( String userName ) {

        return Jwts.builder()
                .setSubject( userName )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + 1000 * 60 * 60 )) // 1 Hour
                .signWith( token )
                .compact();

    }

}
