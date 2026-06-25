package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;

public record PartLeaderCandidateResponse(
        Long candidateId,
        String name,
        Part part,
        String partName,
        String affiliation,
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
