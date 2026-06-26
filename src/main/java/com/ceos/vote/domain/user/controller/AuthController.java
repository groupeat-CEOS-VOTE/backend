package com.ceos.vote.domain.user.controller;

import com.ceos.vote.domain.user.dto.LoginRequest;
import com.ceos.vote.domain.user.dto.LoginResponse;
import com.ceos.vote.domain.user.service.AuthService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    private static final long ACCESS_TOKEN_COOKIE_MAX_AGE_SECONDS = 60 * 60;

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 아이디와 비밀번호를 검증하고 액세스 토큰을 쿠키로 발급합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공. 응답 헤더에 accessToken 쿠키가 포함됩니다.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"COMMON200","message":"성공입니다.","result":{"user":{"id":1,"name":"홍길동","team":"DITDA","part":"BACKEND"}}}
                            """)))
    })
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse servletResponse
    ) {
        AuthService.LoginResult loginResult = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, loginResult.accessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(ACCESS_TOKEN_COOKIE_MAX_AGE_SECONDS)
                .sameSite("Lax")
                .build();

        servletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.onSuccess(loginResult.response());
    }
}
