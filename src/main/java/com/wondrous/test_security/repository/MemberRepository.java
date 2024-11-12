package com.wondrous.test_security.repository;

import com.wondrous.test_security.enrity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberName(String memberName);
    boolean existsByMemberName(String userName);
}
