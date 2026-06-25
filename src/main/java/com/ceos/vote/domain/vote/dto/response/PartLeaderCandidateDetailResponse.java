package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;

public record PartLeaderCandidateDetailResponse(
        Long candidateId,
        String name,
        Part part,
        String partName,
        String affiliation,
        String imageUrl,
        String description
) {

    public static PartLeaderCandidateDetailResponse from(PartLeaderCandidate candidate) {
        return new PartLeaderCandidateDetailResponse(
                candidate.getId(),
                candidate.getName(),
                candidate.getPart(),
                candidate.getPart().getDescription(),
                candidate.getAffiliation(),
                candidate.getImageUrl(),
                candidate.getDescription()
        );
    }
}
