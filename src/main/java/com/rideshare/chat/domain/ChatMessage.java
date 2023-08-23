package com.rideshare.chat.domain;

import lombok.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private long chatId;
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private String time;
    private int roomId;

    public static ChatMessage createChatMessage(String sender, String message, int roomId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.sender = sender;
        chatMessage.message = message;
        chatMessage.roomId = roomId;
        return chatMessage;
    }
}