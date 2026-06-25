package com.ceos.vote.domain.vote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PartLeaderVoteRequest(
        @Schema(description = "투표할 파트장 후보 ID", example = "1")
        @NotNull(message = "투표할 후보를 선택해주세요.")
        Long candidateId
) {
}
