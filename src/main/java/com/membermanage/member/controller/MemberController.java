package com.membermanage.member.controller;

import com.membermanage.member.dto.MemberDTO;
import com.membermanage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor //final이 붙은 필드만 가지고 생성자를 만듬 (생성자 주입)
public class MemberController {
    private final MemberService memberService; //의존성 주입 Controller -> Service -> Repository

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        int saveResult = memberService.save(memberDTO);
        if (saveResult < 0) {
            return "login"; //가입 성공
        }else {
            return "save"; //가입 실패
        }
    }
}
