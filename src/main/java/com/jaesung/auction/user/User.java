package com.jaesung.auction.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** users 테이블 한 행을 담는 객체. MyBatis가 조회 결과를 여기에 채워준다. */
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;   // DB의 password_hash 컬럼 (camelCase 자동 매핑)
    private LocalDateTime createdAt;
}
