package com.jaesung.auction.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** DB나 Spring 없이 JwtProvider만 검증하는 단위 테스트. */
class JwtProviderTest {

    private static String newSecret() {
        return Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
    }

    @Test
    void 발급한_토큰을_파싱하면_사용자_정보가_나온다() {
        JwtProvider provider = new JwtProvider(newSecret(), 60_000L);

        String token = provider.createToken(7L, "tester");
        Claims claims = provider.parse(token);

        assertThat(claims.getSubject()).isEqualTo("7");
        assertThat(claims.get("username", String.class)).isEqualTo("tester");
    }

    @Test
    void 만료된_토큰은_거부된다() {
        JwtProvider provider = new JwtProvider(newSecret(), -1_000L); // 이미 만료된 상태로 발급

        String token = provider.createToken(1L, "tester");

        assertThatThrownBy(() -> provider.parse(token)).isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void 다른_키로_서명된_토큰은_거부된다() {
        JwtProvider issuer = new JwtProvider(newSecret(), 60_000L);
        JwtProvider other = new JwtProvider(newSecret(), 60_000L);

        String token = issuer.createToken(1L, "tester");

        assertThatThrownBy(() -> other.parse(token)).isInstanceOf(JwtException.class);
    }

    @Test
    void 변조된_토큰은_거부된다() {
        JwtProvider provider = new JwtProvider(newSecret(), 60_000L);
        String token = provider.createToken(1L, "tester");

        // 페이로드 부분의 글자 하나를 바꾼다
        String[] parts = token.split("\\.");
        String tamperedPayload = parts[1].substring(0, parts[1].length() - 2) + (parts[1].endsWith("A") ? "B" : "A") + "x";
        String tampered = parts[0] + "." + tamperedPayload + "." + parts[2];

        assertThatThrownBy(() -> provider.parse(tampered)).isInstanceOf(JwtException.class);
    }
}
