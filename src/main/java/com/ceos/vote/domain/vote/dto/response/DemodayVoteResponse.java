package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Team;

public record DemodayVoteResponse(
        Long voteId,
        Team selectedTeam,
        String selectedTeamName
) {
}
