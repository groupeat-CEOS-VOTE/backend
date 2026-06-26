package com.ceos.vote.domain.vote.entity;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_leader_candidates")
public class PartLeaderCandidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Part part;

    @Column(nullable = false, length = 100)
    private String affiliation;

    @Column(length = 500)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    public PartLeaderCandidate(
            String name,
            Part part,
            String affiliation,
            String imageUrl,
            String description
    ) {
        this.name = name;
        this.part = part;
        this.affiliation = affiliation;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
