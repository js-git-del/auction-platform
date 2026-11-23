package com.jaesung.auction.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/** JWT를 만들고(createToken), 검증하며 내용을 꺼내는(parse) 클래스. */
@Component
public class JwtProvider {

    private final SecretKey key;
    private final long expirationMs;

    public JwtProvider(@Value("${jwt.secret}") String base64Secret,
                       @Value("${jwt.expiration-ms}") long expirationMs) {
        // .env의 JWT_SECRET(Base64 문자열)을 서명용 키로 변환
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expirationMs = expirationMs;
    }

    public String createToken(Long userId, String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(userId))          // sub: 사용자 id
                .claim("username", username)              // 페이로드에 담을 정보 (비밀번호 등 민감 정보 금지)
                .issuedAt(now)                            // iat: 발급 시각
                .expiration(new Date(now.getTime() + expirationMs)) // exp: 만료 시각
                .signWith(key)                            // 비밀키로 서명
                .compact();
    }

    /** 서명과 만료를 검증하고 페이로드를 반환. 유효하지 않으면 JwtException을 던진다. */
    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
