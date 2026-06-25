package com.ceos.vote.domain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {

    IPX("IPX"),
    CONX("CONX"),
    GROUPEAT("Groupeat"),
    DITDA("Ditda"),
    JOBDRI("JobDri");

    private final String description;
}
