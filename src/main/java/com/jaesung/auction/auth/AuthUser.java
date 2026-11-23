package com.jaesung.auction.auth;

/** JWT 검증이 끝난 뒤, 요청 안에서 "지금 누구인가"를 나타내는 객체. */
public record AuthUser(Long id, String username) {
}
