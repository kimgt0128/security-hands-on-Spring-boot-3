package com.wondrous.test_security.service;

import com.wondrous.test_security.dto.CustomUserDetails;
import com.wondrous.test_security.enrity.Member;
import com.wondrous.test_security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberName(username);
        log.info(member.toString());
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(member);
    }
}
