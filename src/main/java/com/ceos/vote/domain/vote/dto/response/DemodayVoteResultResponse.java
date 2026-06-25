package com.ceos.vote.domain.vote.dto.response;

import java.util.List;

public record DemodayVoteResultResponse(
        long totalVoterCount,
        long votedCount,
        List<TeamVoteCountResponse> teams
) {
}
