package com.ceos.vote.domain.vote.controller;

import com.ceos.vote.domain.vote.dto.request.PartLeaderVoteRequest;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateDetailResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResultResponse;
import com.ceos.vote.domain.vote.service.PartLeaderVoteService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
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
public class PartLeaderVoteController {

    private final PartLeaderVoteService partLeaderVoteService;

    @GetMapping("/candidates")
    public ApiResponse<List<PartLeaderCandidateResponse>> getCandidates() {
        return ApiResponse.onSuccess(partLeaderVoteService.getCandidates());
    }

    @GetMapping("/candidates/{candidateId}")
    public ApiResponse<PartLeaderCandidateDetailResponse> getCandidate(
            @PathVariable Long candidateId
    ) {
        return ApiResponse.onSuccess(partLeaderVoteService.getCandidate(candidateId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PartLeaderVoteResponse>> vote(
            @Valid @RequestBody PartLeaderVoteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(SuccessStatus.CREATED, partLeaderVoteService.vote(request)));
    }

    @GetMapping("/results")
    public ApiResponse<PartLeaderVoteResultResponse> getResult() {
        return ApiResponse.onSuccess(partLeaderVoteService.getResult());
    }
}
