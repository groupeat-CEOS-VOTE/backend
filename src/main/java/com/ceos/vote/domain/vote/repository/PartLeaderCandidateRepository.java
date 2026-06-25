package com.ceos.vote.domain.vote.repository;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartLeaderCandidateRepository extends JpaRepository<PartLeaderCandidate, Long> {

    List<PartLeaderCandidate> findAllByPartOrderByIdAsc(Part part);

    boolean existsByNameAndPart(String name, Part part);
}
