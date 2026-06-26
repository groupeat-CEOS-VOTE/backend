package com.ceos.vote.domain.vote.exception;

import com.ceos.vote.global.apiPayload.code.BaseErrorCode;
import com.ceos.vote.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VoteErrorStatus implements BaseErrorCode {

    ALREADY_VOTED(HttpStatus.CONFLICT, "VOTE4091", "이미 투표했습니다."),
    CANNOT_VOTE_OWN_TEAM(HttpStatus.FORBIDDEN, "VOTE4032", "본인이 속한 팀에는 투표할 수 없습니다."),
    CANNOT_VOTE_OTHER_PART(HttpStatus.FORBIDDEN, "VOTE4033", "본인 파트의 후보에게만 투표할 수 있습니다."),
    VOTE_RESULT_NOT_OPEN(HttpStatus.FORBIDDEN, "VOTE4031", "아직 모든 투표가 완료되지 않아 결과를 조회할 수 없습니다."),
    CANDIDATE_NOT_FOUND(HttpStatus.NOT_FOUND, "VOTE4041", "후보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
