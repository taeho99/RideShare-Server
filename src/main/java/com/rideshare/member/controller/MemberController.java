package com.rideshare.member.controller;

import com.rideshare.member.domain.*;
import com.rideshare.member.service.MemberService;
import com.rideshare.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody JoinDTO inputData) throws MessagingException {
        log.info("call MemberController.join");
        memberService.join(inputData);
    }

    @GetMapping("/auth")
    public String auth(@ModelAttribute AuthDTO inputData) {
        log.info("email={}, authCode={}", inputData.getId(), inputData.getAuthCode());
        return memberService.auth(inputData);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO inputData) {
        log.info("call MemberController.login");
        return memberService.login(inputData);
    }

    @PostMapping("/logout")
    public void logout() {
        memberService.logout(String.valueOf(SecurityUtil.getCurrentMemberId()));
//        memberService.logout("112");
    }

    @PostMapping("/reissue")
    public TokenDTO reissue(@RequestBody TokenRequestDTO inputData) {
        return memberService.reissue(inputData);
    }

    @GetMapping("/me") // 마이페이지
    public ResponseEntity<Member> findMemberByMId() {
        return ResponseEntity.ok(memberService.findMemberByMId(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{id}")
    public Member findMemberById(@PathVariable String id) {
        return memberService.findMemberById(id);
    }

    @GetMapping("/check")
    public String validDuplicate(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) String nickname) {
        if (id != null && id.length() != 0) {
            return memberService.idCheck(id) ? "exist" : "non-exist";
        } else if (email != null && email.length() != 0) {
            return memberService.emailCheck(email) ? "exist" : "non-exist";
        } else if (nickname != null && nickname.length() != 0) {
            return memberService.nicknameCheck(nickname) ? "exist" : "non-exist";
        } else {
            throw new RuntimeException("파라미터 에러");
        }
    }

}
