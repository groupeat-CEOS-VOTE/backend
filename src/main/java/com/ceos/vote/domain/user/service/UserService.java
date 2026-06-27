package com.ceos.vote.domain.user.service;

import com.ceos.vote.domain.user.dto.SignUpRequest;
import com.ceos.vote.domain.user.entity.User;
import com.ceos.vote.domain.user.exception.UserErrorStatus;
import com.ceos.vote.domain.user.repository.UserRepository;
import com.ceos.vote.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        User user = userRepository.findByNameAndTeamAndPartAndInviteCode(
                        request.name(),
                        request.team(),
                        request.part(),
                        request.inviteCode()
                )
                .orElseThrow(() -> new GeneralException(UserErrorStatus.MEMBER_NOT_FOUND));

        if (user.isRegistered()) {
            throw new GeneralException(UserErrorStatus.MEMBER_ALREADY_REGISTERED);
        }

        if (userRepository.existsByLoginId(request.loginId())) {
            throw new GeneralException(UserErrorStatus.DUPLICATE_LOGIN_ID);
        }

        user.signUp(request.loginId(), passwordEncoder.encode(request.password()));
    }
}
