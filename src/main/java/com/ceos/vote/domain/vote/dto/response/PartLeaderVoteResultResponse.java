package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Part;

import java.util.List;

public record PartLeaderVoteResultResponse(
        Part part,
        String partName,
        List<PartLeaderCandidateVoteCountResponse> candidates
) {
}
