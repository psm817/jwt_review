package com.example.jwt_review.domain.member.service;

import com.example.jwt_review.domain.member.entity.Member;
import com.example.jwt_review.domain.member.repository.MemberRepository;
import com.example.jwt_review.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member join(String username, String password, String mail) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(mail)
                .build();

        memberRepository.save(member);

        return member;
    }

    public String genAccessToken(String username, String password) {
        Member member = findByUsername(username).orElse(null);

        if(member == null) return null;

        if(!passwordEncoder.matches((password), member.getPassword())) {
            return null;
        }

        // 유효기간 1년
        return jwtProvider.genToken(member.toClaims(), 60 * 60 * 24 * 365);
    }
}
