package com.rideshare.chat.controller;

import com.rideshare.chat.domain.ChatMessage;
import com.rideshare.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    // /pub/chat/message로 발행요청
    //발행 요청하면 /sub/chat/room/{roomId}로 메시지 send
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 참여하였습니다!");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
