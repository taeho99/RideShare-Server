package com.rideshare.chat.controller;

import com.rideshare.chat.Service.ChatService;
import com.rideshare.chat.domain.ChatMessage;
import com.rideshare.chat.mapper.ChatMapper;
import com.rideshare.member.controller.MemberController;
import com.rideshare.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;
    private final MemberController memberController;

    // /pub/chat/room/{roomId}으로 발행요청
    //발행 요청하면 /sub/chat/room/{roomId}로 메시지 send
    @MessageMapping("/chat/room/{roomId}")
    public void message(@DestinationVariable String roomId, ChatMessage message) {
        log.info("message={}", message.toString());

        message.setRoomId(Integer.valueOf(roomId));
        chatService.insertMessage(message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, message);

    }

    public void enterChatRoom(int mId, int pId) {
        String nickname = memberController.findMemberByMId(mId).getNickname();
        ChatMessage welcomeMessage = ChatMessage.createChatMessage(nickname, nickname + "님이 채팅방에 입장했습니다!", pId);

        System.out.println("welcomeMessage = " + welcomeMessage);

        message(String.valueOf(pId), welcomeMessage);
    }
}
