package com.example.jwt_review.domain.member.service;

import com.example.jwt_review.domain.member.entity.Member;
import com.example.jwt_review.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
}
