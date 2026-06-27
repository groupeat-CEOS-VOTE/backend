package com.ceos.vote.domain.user.repository;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameAndTeamAndPartAndInviteCode(String name, Team team, Part part, String inviteCode);

    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
