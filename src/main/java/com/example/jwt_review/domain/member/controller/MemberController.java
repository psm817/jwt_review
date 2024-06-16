package com.example.jwt_review.domain.member.controller;

import com.example.jwt_review.domain.member.entity.Member;
import com.example.jwt_review.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public Member login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
        // 테스트용
        resp.addHeader("Authentication", "JWT Token");

        return memberService.findByUsername(loginRequest.getUsername()).orElse(null);
    }
}
