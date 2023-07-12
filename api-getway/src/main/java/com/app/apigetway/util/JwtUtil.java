package com.app.apigetway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtil {

    private static final String secret_key ="TW4KFupD2CLPqF9UacAVljporz5NRswEajhsajhsajshRRRRkajsakhskjdhksajRakkPkjsakskahskPPPajshkjahkjsZZkjhskSHKSH";


    public void isTokenValid(String token){
        Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token);
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
