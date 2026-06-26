package com.ceos.vote.domain.vote.auth;

import com.ceos.vote.domain.user.entity.User;
import com.ceos.vote.domain.user.repository.UserRepository;
import com.ceos.vote.global.apiPayload.code.status.GlobalErrorStatus;
import com.ceos.vote.global.exception.GeneralException;
import com.ceos.vote.global.security.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CookieAuthenticatedUserProvider implements AuthenticatedUserProvider {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";

    private final HttpServletRequest request;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public AuthenticatedUser getCurrentUser() {
        Long userId = jwtProvider.getUserId(getAccessToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GlobalErrorStatus._UNAUTHORIZED));

        return new AuthenticatedUser(
                user.getId(),
                user.getPart(),
                user.getTeam()
        );
    }

    private String getAccessToken() {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
        }

        return Arrays.stream(cookies)
                .filter(cookie -> ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new GeneralException(GlobalErrorStatus._UNAUTHORIZED));
    }
}
