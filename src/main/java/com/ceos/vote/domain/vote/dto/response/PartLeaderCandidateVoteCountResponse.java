package com.ceos.vote.domain.vote.dto.response;

public record PartLeaderCandidateVoteCountResponse(
        Long candidateId,
        String name,
        String affiliation,
        String imageUrl,
        long voteCount
) {
}
