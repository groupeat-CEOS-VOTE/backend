package com.ceos.vote.domain.vote.auth;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.common.enums.Team;

public record AuthenticatedUser(
        Long id,
        Part part,
        Team team
) {
}
