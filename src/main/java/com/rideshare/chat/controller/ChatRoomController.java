package com.rideshare.chat.controller;

import com.rideshare.chat.Service.ChatService;
import com.rideshare.chat.domain.ChatMessage;
import com.rideshare.chat.domain.ChatRoom;
import com.rideshare.chat.mapper.ChatMapper;
import com.rideshare.member.util.SecurityUtil;
import com.rideshare.party.domain.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/rooms") //사용자가 참여한 파티의 모든 채팅방을 반환
    public List<Party> rooms() {
        return chatService.findAllRoom(SecurityUtil.getCurrentMemberId());
    }


    @GetMapping("/list/{roomId}")
    public List<ChatMessage> loadChatList(@PathVariable int roomId) {
        return chatService.getChatList(roomId);
    }

}
