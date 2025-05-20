package com.example.todo_api.follow;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FollowRepositoryTest {
    
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void BeforeAll() {
        Member member1 = new Member("member1@gdg.com", "password1");
        Member member2 = new Member("member2@gdg.com", "password2");
        Member member3 = new Member("member3@gdg.com", "password3");
        Member member4 = new Member("member4@gdg.com", "password4");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
    }

    @Test
    @Transactional
    void followSaveTest() {
        Member member1 = memberRepository.findByEmail("member1@gdg.com");
        Member member2 = memberRepository.findByEmail("member2@gdg.com");

        Follow follow = new Follow(member1, member2);
        followRepository.save(follow);

        Assertions.assertThat(follow.getId()).isNotNull();
    }

    @Test
    @Transactional
    void followNotSaveTest() {
        Member member1 = memberRepository.findByEmail("member1@gdg.com");
        Member member2 = memberRepository.findByEmail("member2@gdg.com");

        Follow follow = new Follow(member1, member2);
        followRepository.save(follow);
        followRepository.flushAndClear();

        Follow follow2 = new Follow(member1, member2);
        followRepository.save(follow2);
        followRepository.flushAndClear();

        Assertions.assertThat(follow2.getId()).isEqualTo(null);
    }


    @Test
    @Transactional
    void followFindByIdTest() {
        Member member1 = memberRepository.findByEmail("member1@gdg.com");
        Member member2 = memberRepository.findByEmail("member2@gdg.com");

        Follow follow = new Follow(member1, member2);
        followRepository.save(follow);
        followRepository.flushAndClear();

        Follow findFollow = followRepository.findById(follow.getId());
        Assertions.assertThat(findFollow.getId()).isEqualTo(follow.getId());
    }

    @Test
    @Transactional
    void findByFollowerTest() {
        Member member1 = memberRepository.findByEmail("member1@gdg.com");
        Member member2 = memberRepository.findByEmail("member2@gdg.com");
        Member member3 = memberRepository.findByEmail("member3@gdg.com");
        Member member4 = memberRepository.findByEmail("member4@gdg.com");

        Follow follow1 = new Follow(member1, member2);
        Follow follow2 = new Follow(member1, member3);
        Follow follow3 = new Follow(member1, member4);

        followRepository.save(follow1);
        followRepository.save(follow2);
        followRepository.save(follow3);

        List<Follow> followerList = followRepository.findByFollower(member1);
        Assertions.assertThat(followerList).hasSize(3);
    }

    @Test
    @Transactional
    void findByFollowingTest() {
        Member member1 = memberRepository.findByEmail("member1@gdg.com");
        Member member2 = memberRepository.findByEmail("member2@gdg.com");
        Member member3 = memberRepository.findByEmail("member3@gdg.com");
        Member member4 = memberRepository.findByEmail("member4@gdg.com");

        Follow follow1 = new Follow(member2, member1);
        Follow follow2 = new Follow(member3, member1);
        Follow follow3 = new Follow(member4, member1);
        
        followRepository.save(follow1);
        followRepository.save(follow2);
        followRepository.save(follow3);
        followRepository.flushAndClear();

        List<Follow> followingList = followRepository.findByFollowing(member1);
        Assertions.assertThat(followingList).hasSize(3);
    }
}
