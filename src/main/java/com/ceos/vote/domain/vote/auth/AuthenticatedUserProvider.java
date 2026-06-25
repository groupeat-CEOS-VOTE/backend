package com.ceos.vote.domain.vote.auth;

public interface AuthenticatedUserProvider {

    AuthenticatedUser getCurrentUser();
}
