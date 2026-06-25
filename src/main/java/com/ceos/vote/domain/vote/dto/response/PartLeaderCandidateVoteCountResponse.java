package com.ceos.vote.domain.vote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PartLeaderCandidateVoteCountResponse(
        @Schema(description = "후보 ID", example = "1")
        Long candidateId,
        @Schema(description = "후보 이름", example = "김동욱")
        String name,
        @Schema(description = "후보 소속", example = "백엔드")
        String affiliation,
        @Schema(description = "후보 사진 URL", example = "https://example.com/images/candidates/1.png")
        String imageUrl,
        @Schema(description = "후보 득표 수", example = "7")
        long voteCount
) {
}
