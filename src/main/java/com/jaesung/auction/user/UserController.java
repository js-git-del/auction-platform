package com.jaesung.auction.user;

import com.jaesung.auction.auth.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /** JWT 인증이 동작하는지 확인하는 용도. 토큰 없이 호출하면 401. */
    @GetMapping("/api/me")
    public AuthUser me(@AuthenticationPrincipal AuthUser user) {
        return user;
    }
}
