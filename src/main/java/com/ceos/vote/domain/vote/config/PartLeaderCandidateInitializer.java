package com.ceos.vote.domain.vote.config;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.vote.entity.PartLeaderCandidate;
import com.ceos.vote.domain.vote.repository.PartLeaderCandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PartLeaderCandidateInitializer implements CommandLineRunner {

    private final PartLeaderCandidateRepository partLeaderCandidateRepository;

    @Override
    @Transactional
    public void run(String... args) {
        getInitialCandidates().stream()
                .filter(candidate -> !partLeaderCandidateRepository.existsByNameAndPart(
                        candidate.getName(),
                        candidate.getPart()
                ))
                .forEach(partLeaderCandidateRepository::save);
    }

    private List<PartLeaderCandidate> getInitialCandidates() {
        return List.of(
                new PartLeaderCandidate(
                        "김동욱",
                        Part.BACKEND,
                        "백엔드",
                        null,
                        "백엔드 파트장 후보 김동욱입니다."
                ),
                new PartLeaderCandidate(
                        "최승원",
                        Part.BACKEND,
                        "백엔드",
                        null,
                        "백엔드 파트장 후보 최승원입니다."
                ),
                new PartLeaderCandidate(
                        "황영준",
                        Part.FRONTEND,
                        "프론트엔드",
                        null,
                        "프론트엔드 파트장 후보 황영준입니다."
                ),
                new PartLeaderCandidate(
                        "이승연",
                        Part.FRONTEND,
                        "프론트엔드",
                        null,
                        "프론트엔드 파트장 후보 이승연입니다."
                )
        );
    }
}
