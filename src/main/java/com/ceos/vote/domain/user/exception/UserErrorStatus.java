package com.ceos.vote.domain.user.exception;

import com.ceos.vote.global.apiPayload.code.BaseErrorCode;
import com.ceos.vote.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorStatus implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4000", "입력하신 정보와 일치하는 회원이 없거나 초대코드가 올바르지 않습니다."),
    MEMBER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "USER4090", "이미 가입된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message).code(code).isSuccess(false).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message).code(code).isSuccess(false).httpStatus(httpStatus).build();
    }
}
