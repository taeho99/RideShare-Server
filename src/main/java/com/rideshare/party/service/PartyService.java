package com.rideshare.party.service;

import com.rideshare.chat.controller.ChatController;
import com.rideshare.member.util.SecurityUtil;
import com.rideshare.party.domain.*;
import com.rideshare.party.mapper.PartyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {

    private final PartyMapper partyMapper;
    private final ChatController chatController;

    public Party partySave(PartyDTO party) {
        partyMapper.partySave(party);
        int currentMemberId = SecurityUtil.getCurrentMemberId();
        int currPId = party.getPId();
        partyMapper.insertMemberHasParty(new MemberHasPartyDTO(currentMemberId, currPId, true));
        chatController.enterChatRoom(currentMemberId, currPId);
        return partyMapper.findById(party.getPId());
    }

    public List<Party> listPage(ScrollDTO scrollDTO) {
        log.info("scrollDTO={}", scrollDTO);
        return partyMapper.listPage(scrollDTO);
    }

    public Party findById(int pId) {
        return partyMapper.findById(pId);
    }

    //TODO 글 작성자만 DELETE 할 수 있게 수정해야함
    public void deleteById(int pId) {
        partyMapper.deleteMemberHasPartyById(pId);
        partyMapper.deleteById(pId);
    }

    public void leaveParty(int pId) {
        partyMapper.deleteMemberHasPartyByMidPid(SecurityUtil.getCurrentMemberId(), pId);
    }

    //TODO 글 작성자만 UPDATE 할 수 있게 수정해야함
    public Party updateById(PartyDTO inputData) {
        partyMapper.updateById(inputData);
        return partyMapper.findById(inputData.getPId());
    }

    public void onConfirm(int pId) {
        MemberHasPartyDTO memberHasPartyDTO = partyMapper.selectMemberHasPartyById(pId); // pId가 일치하는 것 중 isWriter=true 인것만 가져옴
        int writerId = memberHasPartyDTO.getMId();
        int currentMemberId = SecurityUtil.getCurrentMemberId();
        if (writerId != currentMemberId) {
            throw new RuntimeException("파티장이 아닙니다.");
        }
        partyMapper.onConfirm(pId);
    }

    public void onFinish(int pId) {
        MemberHasPartyDTO memberHasPartyDTO = partyMapper.selectMemberHasPartyById(pId); // pId가 일치하는 것 중 isWriter=true 인것만 가져옴
        int writerId = memberHasPartyDTO.getMId();
        int currentMemberId = SecurityUtil.getCurrentMemberId();
        if (writerId != currentMemberId) {
            throw new RuntimeException("파티장이 아닙니다.");
        }
        partyMapper.onFinish(pId);
    }

    public Integer participate(int pId) {
        int totalHeadcnt = partyMapper.findById(pId).getTotalHeadcnt();
        int currentHeadcnt = partyMapper.findById(pId).getCurrentHeadcnt();
        if (currentHeadcnt + 1 > totalHeadcnt) {
            throw new RuntimeException("파티 정원 초과");
        }

        partyMapper.increaseCurrentHeadcnt(pId);
        int currentMemberId = SecurityUtil.getCurrentMemberId();
        partyMapper.insertMemberHasParty(new MemberHasPartyDTO(currentMemberId, pId, false));
        chatController.enterChatRoom(currentMemberId, pId);
        return currentHeadcnt + 1;
    }

    public Integer getCount(String type) {
        return partyMapper.getCount(type);
    }
}
