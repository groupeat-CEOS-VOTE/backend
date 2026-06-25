package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Team;
import io.swagger.v3.oas.annotations.media.Schema;

public record DemodayVoteResponse(
        @Schema(description = "투표 ID", example = "1")
        Long voteId,
        @Schema(description = "투표한 팀", example = "DITDA")
        Team selectedTeam,
        @Schema(description = "투표한 팀 이름", example = "Ditda")
        String selectedTeamName
) {
}
