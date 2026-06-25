package com.ceos.vote.domain.vote.controller;

import com.ceos.vote.domain.vote.dto.request.DemodayVoteRequest;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResultResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteStatusResponse;
import com.ceos.vote.domain.vote.service.DemodayVoteService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
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
public class DemodayVoteController {

    private final DemodayVoteService demodayVoteService;

    @PostMapping
    public ResponseEntity<ApiResponse<DemodayVoteResponse>> vote(
            @Valid @RequestBody DemodayVoteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(SuccessStatus.CREATED, demodayVoteService.vote(request)));
    }

    @GetMapping("/status")
    public ApiResponse<DemodayVoteStatusResponse> getStatus() {
        return ApiResponse.onSuccess(demodayVoteService.getStatus());
    }

    @GetMapping("/results")
    public ApiResponse<DemodayVoteResultResponse> getResult() {
        return ApiResponse.onSuccess(demodayVoteService.getResult());
    }
}
