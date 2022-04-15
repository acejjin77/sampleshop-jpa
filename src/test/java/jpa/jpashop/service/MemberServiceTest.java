package jpa.jpashop.service;

import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.repository.MemberRepository;
import jpa.jpashop.domain.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void memberJoin() throws Exception {
        Member memberTA = new Member();
        memberTA.setName("memberTA");

        Long saveId = memberService.join(memberTA);

        assertEquals(memberTA, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void memberJoin_duplication() throws Exception {
        Member memberTA = new Member();
        memberTA.setName("memberT");


        Member memberTB = new Member();
        memberTB.setName("memberT");

        memberService.join(memberTA);
        memberService.join(memberTB); // Exceptiop

        fail("No exception...");

    }
}
