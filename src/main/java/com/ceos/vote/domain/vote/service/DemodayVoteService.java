package com.ceos.vote.domain.vote.service;

import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.domain.vote.auth.AuthenticatedUser;
import com.ceos.vote.domain.vote.auth.AuthenticatedUserProvider;
import com.ceos.vote.domain.vote.dto.request.DemodayVoteRequest;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResultResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteStatusResponse;
import com.ceos.vote.domain.vote.dto.response.TeamVoteCountResponse;
import com.ceos.vote.domain.vote.entity.DemodayVote;
import com.ceos.vote.domain.vote.exception.VoteErrorStatus;
import com.ceos.vote.domain.vote.repository.DemodayVoteRepository;
import com.ceos.vote.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemodayVoteService {

    private final DemodayVoteRepository demodayVoteRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final DemodayTotalVoterProvider demodayTotalVoterProvider;

    @Transactional
    public DemodayVoteResponse vote(DemodayVoteRequest request) {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();
        Team selectedTeam = request.team();

        validateVote(user, selectedTeam);

        DemodayVote vote = saveVote(user, selectedTeam);

        return new DemodayVoteResponse(
                vote.getId(),
                vote.getSelectedTeam(),
                vote.getSelectedTeam().getDescription()
        );
    }

    public DemodayVoteStatusResponse getStatus() {
        long totalVoterCount = demodayTotalVoterProvider.getTotalVoterCount();
        long votedCount = demodayVoteRepository.count();

        return new DemodayVoteStatusResponse(
                totalVoterCount,
                votedCount,
                isResultOpen(totalVoterCount, votedCount),
                getTeamVoteCounts()
        );
    }

    public DemodayVoteResultResponse getResult() {
        long totalVoterCount = demodayTotalVoterProvider.getTotalVoterCount();
        long votedCount = demodayVoteRepository.count();

        if (!isResultOpen(totalVoterCount, votedCount)) {
            throw new GeneralException(VoteErrorStatus.VOTE_RESULT_NOT_OPEN);
        }

        return new DemodayVoteResultResponse(
                totalVoterCount,
                votedCount,
                getTeamVoteCounts()
        );
    }

    private void validateVote(AuthenticatedUser user, Team selectedTeam) {
        if (user.team() == selectedTeam) {
            throw new GeneralException(VoteErrorStatus.CANNOT_VOTE_OWN_TEAM);
        }

        if (demodayVoteRepository.existsByVoterId(user.id())) {
            throw new GeneralException(VoteErrorStatus.ALREADY_VOTED);
        }
    }

    private DemodayVote saveVote(AuthenticatedUser user, Team selectedTeam) {
        try {
            return demodayVoteRepository.save(
                    new DemodayVote(user.id(), user.team(), selectedTeam)
            );
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(VoteErrorStatus.ALREADY_VOTED);
        }
    }

    private boolean isResultOpen(long totalVoterCount, long votedCount) {
        return totalVoterCount > 0 && votedCount >= totalVoterCount;
    }

    private List<TeamVoteCountResponse> getTeamVoteCounts() {
        Map<Team, Long> voteCounts = demodayVoteRepository.countVotesByTeam()
                .stream()
                .collect(Collectors.toMap(
                        DemodayVoteRepository.TeamVoteCount::getTeam,
                        DemodayVoteRepository.TeamVoteCount::getVoteCount,
                        Long::sum,
                        () -> new EnumMap<>(Team.class)
                ));

        return Arrays.stream(Team.values())
                .map(team -> new TeamVoteCountResponse(
                        team,
                        team.getDescription(),
                        voteCounts.getOrDefault(team, 0L)
                ))
                .sorted(Comparator
                        .comparingLong(TeamVoteCountResponse::voteCount)
                        .reversed()
                        .thenComparing(response -> response.team().ordinal()))
                .toList();
    }
}
