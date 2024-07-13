package com.rc.raceproject.service;

import com.rc.raceproject.domain.Role;
import com.rc.raceproject.entity.Member;
import com.rc.raceproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member addUser(Map<String, Object> params) {
        String username = params.get("username").toString();

        String password = params.get("password").toString();

        Optional<Member> member = memberRepository.findByUsername(username);

        Member m = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        if(member.isEmpty()) {
            memberRepository.save(m);
        }

        System.out.println(m);

        if (m == null) {
            throw new RuntimeException("User could not be saved!"); // 로그 추가 예정
        }

        return m;
    }
}
