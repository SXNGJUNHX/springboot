package com.test.bootsecurity.repository;

import com.test.bootsecurity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    Member findByUsername(String username);
}
