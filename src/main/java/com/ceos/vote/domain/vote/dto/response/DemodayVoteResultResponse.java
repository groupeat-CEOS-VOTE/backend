package com.ceos.vote.domain.vote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DemodayVoteResultResponse(
        @Schema(description = "전체 투표 대상자 수", example = "25")
        long totalVoterCount,
        @Schema(description = "현재 투표 완료자 수", example = "25")
        long votedCount,
        @Schema(description = "팀별 득표 결과")
        List<TeamVoteCountResponse> teams
) {
}
