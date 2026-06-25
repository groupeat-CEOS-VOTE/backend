package com.ceos.vote.domain.user.service;

import com.ceos.vote.domain.user.dto.LoginRequest;
import com.ceos.vote.domain.user.dto.LoginResponse;
import com.ceos.vote.domain.user.dto.UserSummary;
import com.ceos.vote.domain.user.entity.User;
import com.ceos.vote.domain.user.exception.AuthErrorStatus;
import com.ceos.vote.domain.user.repository.UserRepository;
import com.ceos.vote.global.exception.GeneralException;
import com.ceos.vote.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public LoginResult login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new GeneralException(AuthErrorStatus.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new GeneralException(AuthErrorStatus.LOGIN_FAILED);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());
        LoginResponse response = new LoginResponse(UserSummary.from(user));

        return new LoginResult(accessToken, response);
    }

    public record LoginResult(
            String accessToken,
            LoginResponse response
    ) {
    }
}
