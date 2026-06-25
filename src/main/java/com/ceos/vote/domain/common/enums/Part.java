package com.ceos.vote.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Part {

    FRONTEND("프론트엔드"),
    BACKEND("백엔드");

    private final String description;
}
