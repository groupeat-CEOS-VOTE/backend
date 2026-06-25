package com.ceos.vote.domain.user.dto;

import com.ceos.vote.domain.user.entity.User;
import com.ceos.vote.domain.user.enums.Part;
import com.ceos.vote.domain.user.enums.Team;

public record UserSummary(
        Long id,
        String name,
        Team team,
        Part part
) {

    public static UserSummary from(User user) {
        return new UserSummary(
                user.getId(),
                user.getName(),
                user.getTeam(),
                user.getPart()
        );
    }
}
