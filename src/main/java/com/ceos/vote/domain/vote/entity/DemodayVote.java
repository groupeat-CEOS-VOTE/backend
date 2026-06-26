package com.ceos.vote.domain.vote.entity;

import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "demoday_votes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_demoday_votes_voter_id", columnNames = "voter_id")
        }
)
public class DemodayVote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voter_id", nullable = false, updatable = false)
    private Long voterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "voter_team", nullable = false, updatable = false)
    private Team voterTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "selected_team", nullable = false, updatable = false)
    private Team selectedTeam;

    public DemodayVote(Long voterId, Team voterTeam, Team selectedTeam) {
        this.voterId = voterId;
        this.voterTeam = voterTeam;
        this.selectedTeam = selectedTeam;
    }
}
