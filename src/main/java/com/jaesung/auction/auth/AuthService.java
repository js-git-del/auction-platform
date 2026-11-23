package com.jaesung.auction.auth;

import com.jaesung.auction.user.User;
import com.jaesung.auction.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(SignupRequest req) {
        if (userMapper.existsByUsername(req.username())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userMapper.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password())); // 원문이 아닌 BCrypt 해시만 저장
        userMapper.insert(user);
    }

    public TokenResponse login(LoginRequest req) {
        User user = userMapper.findByUsername(req.username());

        // 아이디가 없든 비밀번호가 틀리든 같은 메시지를 준다. (어느 쪽이 틀렸는지 알려주지 않기 위해)
        if (user == null || !passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return TokenResponse.bearer(jwtProvider.createToken(user.getId(), user.getUsername()));
    }
}
