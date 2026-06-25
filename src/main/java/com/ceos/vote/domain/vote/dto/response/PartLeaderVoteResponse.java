package com.ceos.vote.domain.vote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PartLeaderVoteResponse(
        @Schema(description = "투표 ID", example = "1")
        Long voteId,
        @Schema(description = "투표한 후보 ID", example = "1")
        Long candidateId,
        @Schema(description = "투표한 후보 이름", example = "김동욱")
        String candidateName
) {
}
