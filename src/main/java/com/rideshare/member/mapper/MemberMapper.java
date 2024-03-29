package com.rideshare.member.mapper;

import com.rideshare.member.domain.Member;
import com.rideshare.party.domain.Party;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    void join(Member inputData);

    Integer findAuthCodeById(String id);

    void updateAuthStatus(String id);

    Optional<Member> findMemberById(String id);

    Optional<Member> findMemberByMId(int mId);

    boolean idCheck(String id);

    boolean emailCheck(String email);

    boolean nicknameCheck(String nickname);

    List<Party> getNoticeList(int mId);

    List<Party> getParticipationList(int mId);

    void changeNickname(@Param("mId") int mId, @Param("newNickname") String newNickname);

    void changePassword(@Param("mId") int mId, @Param("newPassword") String newPassword);
}
