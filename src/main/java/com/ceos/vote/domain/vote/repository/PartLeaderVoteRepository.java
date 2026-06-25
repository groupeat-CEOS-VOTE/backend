package com.ceos.vote.domain.vote.repository;

import com.ceos.vote.domain.vote.entity.PartLeaderVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartLeaderVoteRepository extends JpaRepository<PartLeaderVote, Long> {

    boolean existsByVoterId(Long voterId);

    @Query("""
            select v.candidate.id as candidateId, count(v.id) as voteCount
            from PartLeaderVote v
            group by v.candidate.id
            """)
    List<CandidateVoteCount> countVotesByCandidate();

    interface CandidateVoteCount {

        Long getCandidateId();

        long getVoteCount();
    }
}
