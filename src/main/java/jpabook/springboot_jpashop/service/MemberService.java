package jpabook.springboot_jpashop.service;

import jpabook.springboot_jpashop.domain.Member;
import jpabook.springboot_jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //모든 메소드가 읽기전용으로 동작, DB부하를 줄여준다
@RequiredArgsConstructor //final이 붙은 객체에 생성자를 달아줌:
// Autowired로 끌어오면 변경이불가하나, 생성자로 불러오면 test시 커스텀이 가능하다.
// final 객체의 생성자이기때문에, 컴파일시 생성자안에 아무것도 집어넣지않으면 에러가 뜬다 >> 에러체크에 용이
public class MemberService {

//    @Autowired 단일 생성자인경우, spring에서 자동으로 Autowired를 달아준다
//    private MemberRepository memberRepository
    private final MemberRepository memberRepository;

    @Transactional  //쓰기가 필요한 메소드는 따로 Transactional을 붙여준다. readOnry = false
    public Long join(Member member){

        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다");

    }

    public List<Member> findMembers(){

        return memberRepository.findAll();
    }

    public Member findMember(Long id){

        return memberRepository.find(id);
    }
}
