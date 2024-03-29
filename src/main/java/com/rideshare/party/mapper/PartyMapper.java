package com.rideshare.party.mapper;

import com.rideshare.party.domain.MemberHasPartyDTO;
import com.rideshare.party.domain.PartyDTO;
import com.rideshare.party.domain.Party;
import com.rideshare.party.domain.ScrollDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartyMapper {
    void partySave(PartyDTO party);

    void insertMemberHasParty(MemberHasPartyDTO memberHasPartyDTO);

    List<Party> listPage(@Param("scroll") ScrollDTO scrollDTO, @Param("mId") int mId);

    Party findById(int pId);

    void deleteById(int pId);

    MemberHasPartyDTO selectMemberHasPartyById(int pId);

    void deleteMemberHasPartyById(int pId);

    void deleteMemberHasPartyByMidPid(@Param("mId") int mId, @Param("pId") int pId);

    void updateById(PartyDTO inputData);

    void onConfirm(int pId);

    void onFinish(int pId);

    void increaseCurrentHeadcnt(int pId);

    Integer getCount(String type);

    boolean isExistUserInParty(@Param("mId") int mId, @Param("pId") int pId);

    void decreaseCurrentHeadCnt(int pId);

    boolean isFinish(int pId);

    boolean isConfirm(int pId);
}
