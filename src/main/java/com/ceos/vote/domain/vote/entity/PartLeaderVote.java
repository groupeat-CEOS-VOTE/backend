package com.ceos.vote.domain.vote.entity;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "part_leader_votes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_part_leader_votes_voter_id", columnNames = "voter_id")
        }
)
public class PartLeaderVote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voter_id", nullable = false, updatable = false)
    private Long voterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "voter_part", nullable = false, updatable = false)
    private Part voterPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false, updatable = false)
    private PartLeaderCandidate candidate;

    public PartLeaderVote(Long voterId, Part voterPart, PartLeaderCandidate candidate) {
        this.voterId = voterId;
        this.voterPart = voterPart;
        this.candidate = candidate;
    }
}
