package com.ceos.vote.domain.vote.dto.request;

import com.ceos.vote.domain.common.enums.Team;
import jakarta.validation.constraints.NotNull;

public record DemodayVoteRequest(
        @NotNull(message = "투표할 팀을 선택해주세요.")
        Team team
) {
}
