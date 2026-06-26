package com.ceos.vote.domain.user.dto;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.common.enums.Team;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "로그인 아이디는 필수입니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @NotNull(message = "팀은 필수입니다.")
        Team team,

        @NotNull(message = "파트는 필수입니다.")
        Part part,

        @NotBlank(message = "초대코드는 필수입니다.")
        String inviteCode
) {
}
