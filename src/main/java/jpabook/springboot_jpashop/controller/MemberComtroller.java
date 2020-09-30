package jpabook.springboot_jpashop.controller;

import jpabook.springboot_jpashop.domain.Address;
import jpabook.springboot_jpashop.domain.Member;
import jpabook.springboot_jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberComtroller {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createForm2(@Valid MemberForm form, BindingResult result) {

        //Valid의 NotEmpty로인해 오류가발생한경우 result는 Error를 가질거고 그 경우, 해당페이지 재 호출
        if (result.hasErrors())
            return "members/createMemberForm";

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
}
