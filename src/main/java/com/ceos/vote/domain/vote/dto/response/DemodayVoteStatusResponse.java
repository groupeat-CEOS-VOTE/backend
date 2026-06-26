package com.ceos.vote.domain.vote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DemodayVoteStatusResponse(
        @Schema(description = "전체 투표 대상자 수", example = "25")
        long totalVoterCount,
        @Schema(description = "현재 투표 완료자 수", example = "12")
        long votedCount,
        @Schema(description = "결과 조회 가능 여부", example = "false")
        boolean resultOpen,
        @Schema(description = "팀별 현재 득표 현황")
        List<TeamVoteCountResponse> teams
) {
}
