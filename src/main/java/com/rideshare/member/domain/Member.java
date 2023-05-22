package com.rideshare.member.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Member {
    private int mId;
    private String id;
    private String pw;
    private String nickname;
    private String email;
    private int authCode;
    private Boolean authStatus;
    private Authority authority;

}
