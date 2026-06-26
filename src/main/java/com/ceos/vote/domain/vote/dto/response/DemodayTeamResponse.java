package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Team;
import io.swagger.v3.oas.annotations.media.Schema;

public record DemodayTeamResponse(
        @Schema(description = "팀 식별자", example = "DITDA")
        Team team,
        @Schema(description = "표시할 팀 이름", example = "Ditda")
        String teamName
) {

    public static DemodayTeamResponse from(Team team) {
        return new DemodayTeamResponse(team, team.getDescription());
    }
}
