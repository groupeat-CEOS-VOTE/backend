package com.ceos.vote.domain.vote.dto.request;

import jakarta.validation.constraints.NotNull;

public record PartLeaderVoteRequest(
        @NotNull(message = "투표할 후보를 선택해주세요.")
        Long candidateId
) {
}
