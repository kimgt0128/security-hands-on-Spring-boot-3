package com.wondrous.test_security.service;

import com.wondrous.test_security.dto.JoinRequestDto;
import com.wondrous.test_security.enrity.Member;
import com.wondrous.test_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDto request) {

        //DB에 이미 동일한 회원이 있는지 확인


        //User 데이터 입력
        Member member = request.toEntity();
        member.setRole("ROLE_USER");

        //비밀 번호 암호화
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));


        //유저 정보 저장
        memberRepository.save(member);
    }
}
