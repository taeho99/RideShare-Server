package com.rideshare.member.controller;

import com.rideshare.member.domain.*;
import com.rideshare.member.service.MemberService;
import com.rideshare.member.util.SecurityUtil;
import com.rideshare.party.domain.Party;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.util.List;

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
    public ResponseEntity<Member> findMyPage() {
        return ResponseEntity.ok(memberService.findMemberByMId(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/id/{id}")
    public Member findMemberById(@PathVariable String id) {
        return memberService.findMemberById(id);
    }

    @GetMapping("/mid/{mid}")
    public Member findMemberByMId(@PathVariable int mid) {
        return memberService.findMemberByMId(mid);
    }

    @GetMapping("/check")
    public void validDuplicate(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) String nickname) {
        if (id != null && id.length() != 0) {
            if (memberService.idCheck(id))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID 중복");
        } else if (email != null && email.length() != 0) {
            if (memberService.emailCheck(email))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일 중복");
        } else if (nickname != null && nickname.length() != 0) {
            if (memberService.nicknameCheck(nickname))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "닉네임 중복");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파라미터 에러");
        }
    }

    @GetMapping("/notice-list")
    public List<Party> myNoticeList() {
        return memberService.getNoticeList(SecurityUtil.getCurrentMemberId());
    }

    @GetMapping("/participation-list")
    public List<Party> myParticipationList() {
        return memberService.getPaticipationList(SecurityUtil.getCurrentMemberId());
    }

    @PutMapping("/nickname")
    public void changeNewNickname(@RequestBody NicknameDTO nicknameDTO) {
        memberService.changeNickname(SecurityUtil.getCurrentMemberId(), nicknameDTO.getNickname());
    }

    @PutMapping("/password")
    public void changeNewPassword(@RequestBody PasswordDTO passwordDTO) {
        memberService.changePassword(SecurityUtil.getCurrentMemberId(), passwordDTO.getOldPassword(), passwordDTO.getNewPassword());
    }
}
