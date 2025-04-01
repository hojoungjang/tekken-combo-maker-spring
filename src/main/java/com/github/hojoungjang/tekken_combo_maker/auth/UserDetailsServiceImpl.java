package com.github.hojoungjang.tekken_combo_maker.auth;

import com.github.hojoungjang.tekken_combo_maker.common.exception.InvalidLoginMethodException;
import com.github.hojoungjang.tekken_combo_maker.member.model.entity.Member;
import com.github.hojoungjang.tekken_combo_maker.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, InvalidLoginMethodException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with email: %s", email)
                ));

        // Invalid login: 소셜 로그인으로 가입한 유저는 비번 로그인을 할 수 없음
        if (member.getOauthProvider() != null) {
            throw new InvalidLoginMethodException("OAuth user cannot login with password");
        }

        return new User(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("user")));
    }
}
