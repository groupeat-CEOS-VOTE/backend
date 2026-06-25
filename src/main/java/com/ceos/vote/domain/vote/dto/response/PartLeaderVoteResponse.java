package com.ceos.vote.domain.vote.dto.response;

public record PartLeaderVoteResponse(
        Long voteId,
        Long candidateId,
        String candidateName
) {
}
