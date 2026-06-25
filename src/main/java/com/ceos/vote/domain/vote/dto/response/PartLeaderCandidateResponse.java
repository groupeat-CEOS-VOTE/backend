package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;
import io.swagger.v3.oas.annotations.media.Schema;

public record PartLeaderCandidateResponse(
        @Schema(description = "후보 ID", example = "1")
        Long candidateId,
        @Schema(description = "후보 이름", example = "김동욱")
        String name,
        @Schema(description = "후보 파트", example = "BACKEND")
        Part part,
        @Schema(description = "후보 파트 이름", example = "백엔드")
        String partName,
        @Schema(description = "후보 소속", example = "백엔드")
        String affiliation,
        @Schema(description = "후보 사진 URL", example = "https://example.com/images/candidates/1.png")
        String imageUrl
) {

    public static PartLeaderCandidateResponse from(PartLeaderCandidate candidate) {
        return new PartLeaderCandidateResponse(
                candidate.getId(),
                candidate.getName(),
                candidate.getPart(),
                candidate.getPart().getDescription(),
                candidate.getAffiliation(),
                candidate.getImageUrl()
        );
    }
}
