package com.ceos.vote.domain.vote.repository;

import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.domain.vote.entity.DemodayVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemodayVoteRepository extends JpaRepository<DemodayVote, Long> {

    boolean existsByVoterId(Long voterId);

    long countBySelectedTeam(Team selectedTeam);

    @Query("""
            select v.selectedTeam as team, count(v.id) as voteCount
            from DemodayVote v
            group by v.selectedTeam
            """)
    List<TeamVoteCount> countVotesByTeam();

    interface TeamVoteCount {

        Team getTeam();

        long getVoteCount();
    }
}
