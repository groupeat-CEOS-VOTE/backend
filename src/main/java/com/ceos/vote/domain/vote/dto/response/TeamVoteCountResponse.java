package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Team;

public record TeamVoteCountResponse(
        Team team,
        String teamName,
        long voteCount
) {
}
