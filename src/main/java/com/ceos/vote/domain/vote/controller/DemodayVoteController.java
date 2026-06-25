package com.ceos.vote.domain.vote.controller;

import com.ceos.vote.domain.vote.dto.request.DemodayVoteRequest;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResultResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteStatusResponse;
import com.ceos.vote.domain.vote.service.DemodayVoteService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/demoday")
@Tag(name = "데모데이 투표", description = "데모데이 팀 투표 참여, 현황 조회, 결과 조회 API")
public class DemodayVoteController {

    private final DemodayVoteService demodayVoteService;

    @Operation(
            summary = "데모데이 투표 참여",
            description = "로그인한 사용자가 본인 팀을 제외한 데모데이 참여 팀에 한 번 투표합니다."
    )
    @Parameter(name = "X-USER-ID", in = ParameterIn.HEADER, required = true, example = "1", description = "임시 인증 사용자 ID")
    @Parameter(name = "X-USER-PART", in = ParameterIn.HEADER, required = true, example = "BACKEND", description = "임시 인증 사용자 파트")
    @Parameter(name = "X-USER-TEAM", in = ParameterIn.HEADER, required = true, example = "GROUPEAT", description = "임시 인증 사용자 소속 팀")
    @PostMapping
    public ResponseEntity<ApiResponse<DemodayVoteResponse>> vote(
            @Valid @RequestBody DemodayVoteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(SuccessStatus.CREATED, demodayVoteService.vote(request)));
    }

    @Operation(
            summary = "데모데이 투표 현황 조회",
            description = "팀별 현재 득표 수를 내림차순으로 조회합니다. 모든 투표 완료 여부도 함께 반환합니다."
    )
    @GetMapping("/status")
    public ApiResponse<DemodayVoteStatusResponse> getStatus() {
        return ApiResponse.onSuccess(demodayVoteService.getStatus());
    }

    @Operation(
            summary = "데모데이 투표 결과 조회",
            description = "모든 회원의 투표가 완료된 뒤 데모데이 투표 결과를 조회합니다."
    )
    @GetMapping("/results")
    public ApiResponse<DemodayVoteResultResponse> getResult() {
        return ApiResponse.onSuccess(demodayVoteService.getResult());
    }
}
