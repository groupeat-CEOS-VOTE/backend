package com.ceos.vote.domain.vote.dto.response;

import com.ceos.vote.domain.common.enums.Part;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PartLeaderVoteResultResponse(
        @Schema(description = "결과 조회 파트", example = "BACKEND")
        Part part,
        @Schema(description = "결과 조회 파트 이름", example = "백엔드")
        String partName,
        @Schema(description = "후보별 득표 결과")
        List<PartLeaderCandidateVoteCountResponse> candidates
) {
}
