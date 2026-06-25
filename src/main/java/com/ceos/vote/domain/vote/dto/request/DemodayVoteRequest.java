package com.ceos.vote.domain.vote.dto.request;

import com.ceos.vote.domain.common.enums.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record DemodayVoteRequest(
        @Schema(description = "투표할 데모데이 팀", example = "DITDA")
        @NotNull(message = "투표할 팀을 선택해주세요.")
        Team team
) {
}
