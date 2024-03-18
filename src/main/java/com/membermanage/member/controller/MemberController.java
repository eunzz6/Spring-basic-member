package com.membermanage.member.controller;

import com.membermanage.member.dto.MemberDTO;
import com.membermanage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.util.List;

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

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        } else{ return"login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    // /member?id=1
    @GetMapping
    public String findById(@RequestParam("id")Long id, Model model){
        MemberDTO memberDTO = memberService.fidnById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){
        memberService.delete(id);
        return "redirect:/member/";
    }

    //수정화면 요청
    @GetMapping("/update")
    public String updateFrom(HttpSession session, Model model){
        //세션에 저장된 나의 이메일 가져오기
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        boolean result = memberService.update(memberDTO);
        if (result){
            return "redirect:/member?id="+memberDTO.getId();
        } else {
          return "index";
        }
    }

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }


}
