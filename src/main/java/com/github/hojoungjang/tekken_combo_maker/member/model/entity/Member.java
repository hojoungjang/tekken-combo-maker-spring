package com.github.hojoungjang.tekken_combo_maker.member.model.entity;

import com.github.hojoungjang.tekken_combo_maker.common.model.entity.AuditFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickName;

    private String oauthProvider;

    private String oauthProviderId;

    @Builder
    public Member(
            String email,
            String password,
            String nickName,
            String oauthProvider,
            String oauthProviderId
    ) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }
}
