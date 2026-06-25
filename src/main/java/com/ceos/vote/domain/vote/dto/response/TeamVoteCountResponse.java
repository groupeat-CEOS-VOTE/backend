package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Team;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeamVoteCountResponse(
        @Schema(description = "팀", example = "DITDA")
        Team team,
        @Schema(description = "팀 이름", example = "Ditda")
        String teamName,
        @Schema(description = "팀 득표 수", example = "8")
        long voteCount
) {
}
