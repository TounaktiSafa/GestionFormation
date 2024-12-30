package com.Project.GestionsFormation.Controller;


import com.Project.GestionsFormation.dto.ChatMessage;
import com.Project.GestionsFormation.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.tree.SqmNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @MessageMapping("/chat.sendChatMessage")
    public ChatMessage sendChatMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        log.info("Sending chat message from: {}", chatMessage.getUserName());
        redisTemplate.convertAndSend("chat", chatMessage); // Assurez-vous que le canal est correct
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getUserName());
        chatMessage.setMessageType(MessageType.JOIN);
        chatMessage.setMessage(chatMessage.getUserName() + " joined the chat");
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        log.info("User joined: {}", chatMessage.getUserName());
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
        return chatMessage;
    }
    @MessageMapping("/video.offer")
    @SendTo("/video/offer")
    public String handleOffer(@Payload String offer) {
        return offer;
    }

    @MessageMapping("/video.answer")
    @SendTo("/video/answer")
    public String handleAnswer(@Payload String answer) {
        return answer;
    }

    @MessageMapping("/video.ice")
    @SendTo("/video/ice")
    public String handleIceCandidate(@Payload String candidate) {
        return candidate;
    }

}