package com.ceos.vote.domain.vote.dto.response;

import java.util.List;

public record DemodayVoteStatusResponse(
        long totalVoterCount,
        long votedCount,
        boolean resultOpen,
        List<TeamVoteCountResponse> teams
) {
}
