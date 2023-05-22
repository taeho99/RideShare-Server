package com.rideshare.member.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {
    private String id;
    private String pw;
    private String nickname;
    private String email;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .pw(passwordEncoder.encode(pw))
                .nickname(nickname)
                .email(email)
                .authCode(new Random().nextInt(888888) + 111111)
                .authStatus(false)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
