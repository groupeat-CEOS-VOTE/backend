package com.ceos.vote.domain.vote.auth;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.global.apiPayload.code.status.GlobalErrorStatus;
import com.ceos.vote.global.exception.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class HeaderAuthenticatedUserProvider implements AuthenticatedUserProvider {

    private static final String USER_ID_HEADER = "X-USER-ID";
    private static final String USER_PART_HEADER = "X-USER-PART";
    private static final String USER_TEAM_HEADER = "X-USER-TEAM";

    private final HttpServletRequest request;

    @Override
    public AuthenticatedUser getCurrentUser() {
        return new AuthenticatedUser(
                parseUserId(getRequiredHeader(USER_ID_HEADER)),
                parsePart(getRequiredHeader(USER_PART_HEADER)),
                parseTeam(getRequiredHeader(USER_TEAM_HEADER))
        );
    }

    private String getRequiredHeader(String name) {
        String value = request.getHeader(name);
        if (!StringUtils.hasText(value)) {
            throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
        }
        return value.trim();
    }

    private Long parseUserId(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new GeneralException(GlobalErrorStatus._BAD_REQUEST);
        }
    }

    private Part parsePart(String value) {
        try {
            return Part.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeneralException(GlobalErrorStatus._BAD_REQUEST);
        }
    }

    private Team parseTeam(String value) {
        try {
            return Team.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeneralException(GlobalErrorStatus._BAD_REQUEST);
        }
    }
}
