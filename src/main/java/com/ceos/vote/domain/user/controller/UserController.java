package com.ceos.vote.domain.user.controller;

import com.ceos.vote.domain.user.dto.SignUpRequest;
import com.ceos.vote.domain.user.service.UserService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이름, 파트, 팀, 추천 코드를 요청해 회원가입을 진행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"USER2000","message":"회원가입이 완료되었습니다.","result":null}
                            """)))
    })
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ApiResponse.of(SuccessStatus.SIGN_UP, null);
    }
}
