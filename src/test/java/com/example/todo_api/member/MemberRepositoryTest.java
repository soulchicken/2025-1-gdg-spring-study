package com.example.todo_api.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void saveTest() {
        Member member1 = new Member("member1@gdg.com", "password1");
        memberRepository.save(member1);
        Assertions.assertThat(member1.getId()).isNotNull();
    }

    @Test
    @Transactional
    void findByIdTest() {
        Member member1 = new Member("member1@gdg.com", "password1");
        memberRepository.save(member1);
        memberRepository.flushAndClear();

        Member findMember = memberRepository.findById(member1.getId());

        Assertions.assertThat(findMember.getEmail()).isEqualTo("member1@gdg.com");
        Assertions.assertThat(findMember.getPassword()).isEqualTo("password1");
    }

    @Test
    @Transactional
    void findByEmailTest() {
        Member member1 = new Member("member1@gdg.com", "password1");
        memberRepository.save(member1);
        memberRepository.flushAndClear();

        Member findMember = memberRepository.findByEmail("member1@gdg.com");

        Assertions.assertThat(findMember.getPassword()).isEqualTo("password1");
    }

    @Test
    @Transactional
    void updateTest() {
        Member member1 = new Member("member1@gdg.com", "password1");
        memberRepository.save(member1);
        memberRepository.flushAndClear();

        Member findMember = memberRepository.findById(member1.getId());
        findMember.updatePassword("newPassword");
        memberRepository.flushAndClear();

        Member updatedMember = memberRepository.findById(member1.getId());
        Assertions.assertThat(updatedMember.getPassword()).isEqualTo("newPassword");
    }

    @Test
    @Transactional
    void deleteByIdTest() {
        Member member1 = new Member("member1@gdg.com", "password1");
        memberRepository.save(member1);
        memberRepository.flushAndClear();

        memberRepository.deleteById(member1.getId());
        Member findMember = memberRepository.findById(member1.getId());

        Assertions.assertThat(findMember).isNull();
    }
}
