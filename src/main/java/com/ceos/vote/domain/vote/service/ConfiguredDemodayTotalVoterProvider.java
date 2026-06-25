package com.ceos.vote.domain.vote.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfiguredDemodayTotalVoterProvider implements DemodayTotalVoterProvider {

    private final long totalVoterCount;

    public ConfiguredDemodayTotalVoterProvider(
            @Value("${vote.demoday.total-voter-count:0}") long totalVoterCount
    ) {
        this.totalVoterCount = totalVoterCount;
    }

    @Override
    public long getTotalVoterCount() {
        return totalVoterCount;
    }
}
