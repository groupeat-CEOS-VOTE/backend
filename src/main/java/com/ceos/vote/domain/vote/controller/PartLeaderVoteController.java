package com.ceos.vote.domain.vote.controller;

import com.ceos.vote.domain.vote.dto.request.PartLeaderVoteRequest;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateDetailResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResultResponse;
import com.ceos.vote.domain.vote.service.PartLeaderVoteService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/part-leader")
@Tag(name = "파트장 투표", description = "파트장 후보 조회, 투표 참여, 결과 조회 API")
public class PartLeaderVoteController {

    private final PartLeaderVoteService partLeaderVoteService;

    @Operation(
            summary = "파트장 후보 목록 조회",
            description = "로그인한 사용자의 파트에 해당하는 파트장 후보 목록을 조회합니다."
    )
    @SecurityRequirement(name = "accessTokenCookie")
    @GetMapping("/candidates")
    public ApiResponse<List<PartLeaderCandidateResponse>> getCandidates() {
        return ApiResponse.onSuccess(partLeaderVoteService.getCandidates());
    }

    @Operation(
            summary = "파트장 후보 상세 조회",
            description = "선택한 파트장 후보의 이름, 소속, 사진, 소개 정보를 조회합니다."
    )
    @GetMapping("/candidates/{candidateId}")
    public ApiResponse<PartLeaderCandidateDetailResponse> getCandidate(
            @Parameter(description = "후보 ID", example = "1")
            @PathVariable Long candidateId
    ) {
        return ApiResponse.onSuccess(partLeaderVoteService.getCandidate(candidateId));
    }

    @Operation(
            summary = "파트장 투표 참여",
            description = "로그인한 사용자가 본인 파트의 파트장 후보에게 한 번 투표합니다."
    )
    @SecurityRequirement(name = "accessTokenCookie")
    @PostMapping
    public ResponseEntity<ApiResponse<PartLeaderVoteResponse>> vote(
            @Valid @RequestBody PartLeaderVoteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(SuccessStatus.CREATED, partLeaderVoteService.vote(request)));
    }

    @Operation(
            summary = "파트장 투표 결과 조회",
            description = "로그인한 사용자의 파트에 해당하는 파트장 투표 결과를 득표 수 내림차순으로 조회합니다."
    )
    @SecurityRequirement(name = "accessTokenCookie")
    @GetMapping("/results")
    public ApiResponse<PartLeaderVoteResultResponse> getResult() {
        return ApiResponse.onSuccess(partLeaderVoteService.getResult());
    }
}
