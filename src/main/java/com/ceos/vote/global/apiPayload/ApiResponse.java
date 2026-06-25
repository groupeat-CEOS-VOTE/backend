package com.ceos.vote.global.apiPayload;

import com.ceos.vote.global.apiPayload.code.BaseCode;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@AllArgsConstructor
@Getter
public class ApiResponse<T> {

    private Boolean isSuccess;
    private String code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T data) {
        return new ApiResponse<>(
                true,
                code.getReason().getCode(),
                code.getReason().getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
