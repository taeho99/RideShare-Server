package com.rideshare.chat.Service;

import com.rideshare.chat.controller.ChatController;
import com.rideshare.chat.domain.ChatMessage;
import com.rideshare.chat.domain.ChatRoom;
import com.rideshare.chat.mapper.ChatMapper;
import com.rideshare.member.service.MemberService;
import com.rideshare.party.domain.Party;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatMapper chatMapper;

    //참여중인 파티 목록을 반환하면 됨
    public List<Party> findAllRoom(int mId) {
        return chatMapper.getChatRoomList(mId);
    }

    public List<ChatMessage> getChatList(int pId) {
        return chatMapper.getChatList(pId);
    }


    public void insertMessage(ChatMessage message) {
        chatMapper.insertMessage(message);
    }
}
