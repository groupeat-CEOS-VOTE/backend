package com.ceos.vote.domain.vote.service;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.auth.AuthenticatedUser;
import com.ceos.vote.domain.vote.auth.AuthenticatedUserProvider;
import com.ceos.vote.domain.vote.dto.request.PartLeaderVoteRequest;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateDetailResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderCandidateVoteCountResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResponse;
import com.ceos.vote.domain.vote.dto.response.PartLeaderVoteResultResponse;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;
import com.ceos.vote.domain.vote.entity.PartLeaderVote;
import com.ceos.vote.domain.vote.exception.VoteErrorStatus;
import com.ceos.vote.domain.vote.repository.PartLeaderCandidateRepository;
import com.ceos.vote.domain.vote.repository.PartLeaderVoteRepository;
import com.ceos.vote.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartLeaderVoteService {

    private final PartLeaderCandidateRepository partLeaderCandidateRepository;
    private final PartLeaderVoteRepository partLeaderVoteRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public List<PartLeaderCandidateResponse> getCandidates() {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();

        return partLeaderCandidateRepository.findAllByPartOrderByIdAsc(user.part())
                .stream()
                .map(PartLeaderCandidateResponse::from)
                .toList();
    }

    public PartLeaderCandidateDetailResponse getCandidate(Long candidateId) {
        PartLeaderCandidate candidate = getCandidateById(candidateId);
        return PartLeaderCandidateDetailResponse.from(candidate);
    }

    @Transactional
    public PartLeaderVoteResponse vote(PartLeaderVoteRequest request) {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();
        PartLeaderCandidate candidate = getCandidateById(request.candidateId());

        validateVote(user, candidate);

        PartLeaderVote vote = saveVote(user.id(), user.part(), candidate);

        return new PartLeaderVoteResponse(
                vote.getId(),
                candidate.getId(),
                candidate.getName()
        );
    }

    public PartLeaderVoteResultResponse getResult() {
        AuthenticatedUser user = authenticatedUserProvider.getCurrentUser();
        Part part = user.part();

        return new PartLeaderVoteResultResponse(
                part,
                part.getDescription(),
                getCandidateVoteCounts(part)
        );
    }

    private PartLeaderCandidate getCandidateById(Long candidateId) {
        return partLeaderCandidateRepository.findById(candidateId)
                .orElseThrow(() -> new GeneralException(VoteErrorStatus.CANDIDATE_NOT_FOUND));
    }

    private void validateVote(AuthenticatedUser user, PartLeaderCandidate candidate) {
        if (candidate.getPart() != user.part()) {
            throw new GeneralException(VoteErrorStatus.CANNOT_VOTE_OTHER_PART);
        }

        if (partLeaderVoteRepository.existsByVoterId(user.id())) {
            throw new GeneralException(VoteErrorStatus.ALREADY_VOTED);
        }
    }

    private PartLeaderVote saveVote(Long voterId, Part voterPart, PartLeaderCandidate candidate) {
        try {
            return partLeaderVoteRepository.save(
                    new PartLeaderVote(voterId, voterPart, candidate)
            );
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(VoteErrorStatus.ALREADY_VOTED);
        }
    }

    private List<PartLeaderCandidateVoteCountResponse> getCandidateVoteCounts(Part part) {
        Map<Long, Long> voteCounts = partLeaderVoteRepository.countVotesByCandidate()
                .stream()
                .collect(Collectors.toMap(
                        PartLeaderVoteRepository.CandidateVoteCount::getCandidateId,
                        PartLeaderVoteRepository.CandidateVoteCount::getVoteCount
                ));

        return partLeaderCandidateRepository.findAllByPartOrderByIdAsc(part)
                .stream()
                .map(candidate -> new PartLeaderCandidateVoteCountResponse(
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getAffiliation(),
                        candidate.getImageUrl(),
                        voteCounts.getOrDefault(candidate.getId(), 0L)
                ))
                .sorted(Comparator
                        .comparingLong(PartLeaderCandidateVoteCountResponse::voteCount)
                        .reversed()
                        .thenComparing(PartLeaderCandidateVoteCountResponse::candidateId))
                .toList();
    }
}
