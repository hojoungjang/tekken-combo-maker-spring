package com.github.hojoungjang.tekken_combo_maker.auth.oauth2;

import com.github.hojoungjang.tekken_combo_maker.auth.oauth2.user.GoogleUserInfo;
import com.github.hojoungjang.tekken_combo_maker.auth.oauth2.user.OAuth2UserInfo;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(
            @Qualifier("bcryptPasswordEncoder")PasswordEncoder passwordEncoder,
            MemberRepository memberRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    private Optional<OAuth2UserInfo> getOAuth2UserInfo(
            OAuth2User user,
            String registrationId
    ) {
        // TODO: Change string literals to constants
        switch (registrationId) {
            case "google":
                return Optional.of(new GoogleUserInfo(user.getAttributes()));
            default:
                return Optional.empty();
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = getOAuth2UserInfo(user, registrationId)
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        String.format("Unsupported OAuth provider: %s", registrationId)
                ));

        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String email = userInfo.getEmail();

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(email)
                            .oauthProvider(provider)
                            .oauthProviderId(providerId)
                            .build();
                    memberRepository.save(newMember);
                    return newMember;
                });
        return user;
    }
}
