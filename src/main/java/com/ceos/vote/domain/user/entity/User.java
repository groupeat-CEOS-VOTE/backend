package com.ceos.vote.domain.user.entity;

import com.ceos.vote.domain.common.enums.Part;
import com.ceos.vote.domain.common.enums.Team;
import com.ceos.vote.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Part part;

    @Column(nullable = false, length = 100)
    private String inviteCode;

    @Column(length = 50)
    private String loginId;

    @Column(length = 255)
    private String password;

    public boolean isRegistered() {
        return loginId != null || password != null;
    }

    public void signUp(String loginId, String encodedPassword) {
        this.loginId = loginId;
        this.password = encodedPassword;
    }
}
