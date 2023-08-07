package com.rideshare.member.service;

import com.rideshare.member.domain.*;
import com.rideshare.member.jwt.TokenProvider;
import com.rideshare.member.mapper.MemberMapper;
import com.rideshare.member.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenMapper refreshTokenMapper;
    private final JavaMailSender javaMailSender;

    /* ===== 회원가입 관련 메서드 시작 ===== */
    @Transactional
    public void join(JoinDTO inputData) throws MessagingException {
        if (memberMapper.idCheck(inputData.getId())
           || memberMapper.nicknameCheck(inputData.getNickname())
           || memberMapper.emailCheck(inputData.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 유저입니다.");
        }
        Member member = inputData.toMember(passwordEncoder);
        memberMapper.join(member);
        sendMail(member);
        log.info("member = {}", member);
    }

    public void sendMail(Member member) throws MessagingException {
        String fromMail = "knu.rideshare@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toMail = member.getEmail(); //받는 사람
        String title = "RideShare 회원가입 인증 링크"; //제목
        String authLink = "http://localhost:8080/members/auth?id=" + member.getId() + "&authCode=" + member.getAuthCode();
        String text =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<div style=\"margin:100px;\">\n" +
                "    <h1> 안녕하세요.</h1>\n" +
                "    <h1> 택시팟/카풀 매칭 서비스 RideShare 입니다.</h1>\n" +
                "    <br>\n" +
                "        <p> 아래 링크를 클릭해 회원가입을 완료해 주세요.</p>\n" +
                "    <br>\n" +
                "    <div align=\"center\" style=\"border:1px solid black; font-family:verdana;\">\n" +
                "    <h3> " + "<a href=\"" + authLink + "\">" + authLink + "</h3>\n" +
                "    </div>\n" +
                "    <br/>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(fromMail); //보내는 이메일
        message.addRecipients(MimeMessage.RecipientType.TO, toMail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setText(text, "utf-8", "html");

        javaMailSender.send(message);
    }

    public String auth(AuthDTO inputData) {
        log.info("AuthDTO={}", inputData);
        Integer dbAuthCode = memberMapper.findAuthCodeById(inputData.getId()); //데이터베이스에 저장된 authCode
        if (dbAuthCode == null || inputData.getAuthCode() != dbAuthCode) {
            return "failure";
        } else {
            memberMapper.updateAuthStatus(inputData.getId());
            return "success";
        }
    }

    /* ===== 회원가입 관련 메서드 끝 ===== */

    /* ===== 로그인 관련 메서드 시작 ===== */
    @Transactional
    public TokenDTO login(LoginDTO inputData) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = inputData.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenMapper.delete(refreshToken.getKey());
        refreshTokenMapper.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }
    /* ===== 로그인 관련 메서드 끝 ===== */

    /* ===== 로그아웃 관련 메서드 시작 ==== */
    @Transactional
    public void logout(String mId) {
        refreshTokenMapper.findByKey(mId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));
        refreshTokenMapper.delete(mId);
    }
    /* ===== 로그아웃 관련 메서드 끝 ==== */

    // TODO 로그아웃 됐는데 왜 마이페이지 조회됨? 해결해야함

    /* ===== Refresh_Token 재발급 관련 메서드 시작 ===== */
    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        log.info("authentication.getName() = {}", authentication.getName());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenMapper.findByKey(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                "로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Refresh Token이 일치하지 않거나 만료되었습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenMapper.update(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
    /* ===== Refresh_Token 재발급 관련 메서드 끝 ===== */

    public Member findMemberById(String id) {
        return memberMapper.findMemberById(id)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public Member findMemberByMId(int mId) {
        return memberMapper.findMemberByMId(mId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "로그인 유저 정보가 없습니다."));
    }

    public boolean idCheck(String id) {
        return memberMapper.idCheck(id);
    }

    public boolean emailCheck(String email) {
        return memberMapper.emailCheck(email);
    }

    public boolean nicknameCheck(String nickname) {
        return memberMapper.nicknameCheck(nickname);
    }
}
