package jpabook.springboot_jpashop.service;

import jpabook.springboot_jpashop.domain.Member;
import jpabook.springboot_jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //스프링이랑 JUNIT이랑 엮어서 실행
@SpringBootTest //SpringBoot를 띄운상태로 테스트
@Transactional //pk가 같으면 같은 객체로 인식시켜줌
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {

        Member member = new Member();
        member.setName("Jun");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.find(savedId));
    }

    @Test(expected = IllegalStateException.class) //==catch(IllegalStateException)
    public void 중복회원예외() throws Exception {

        Member member = new Member();
        member.setName("jun");

        Member member2 = new Member();
        member2.setName("jun");

        memberService.join(member);
        memberService.join(member2);


//        fail("fail을 사용해 인위적으로 에러를 낼 수 있다.");
    }
}