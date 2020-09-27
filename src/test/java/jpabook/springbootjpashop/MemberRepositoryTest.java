package jpabook.springbootjpashop;

import jpabook.springboot_jpashop.repository.MemberRepository;
import jpabook.springboot_jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)//test는 테스트가끝나면 자동으로 데이터를 롤백해서 db에 남지않는데, false해주면 남음
    public void testMember() throws Exception {

        Member member = new Member();
        member.setName("Junseok");

        Long savedId = memberRepository.save(member);

        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        
        //객체끼리의 equals는 주소값을 비교하므로 원래라면 false가 나오지만,
        //jpa는 영속성컨텍스트를 통해 비교하므로 같은 id값이면 true가 나옴
        Assertions.assertThat(findMember).isEqualTo(member);

        //그래서 findMember를통해 3가지 비교를 했지만, select쿼리조차 안나감
    }
}