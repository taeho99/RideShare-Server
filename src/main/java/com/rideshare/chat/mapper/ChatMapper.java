package com.rideshare.chat.mapper;

import com.rideshare.chat.domain.ChatMessage;
import com.rideshare.chat.domain.ChatRoom;
import com.rideshare.party.domain.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    void insertMessage(ChatMessage chatMessage);

    List<Party> getChatRoomList(int mId);

    List<ChatMessage> getChatList(int pId);
}
