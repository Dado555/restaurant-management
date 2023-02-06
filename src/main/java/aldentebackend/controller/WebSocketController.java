package aldentebackend.controller;

import aldentebackend.dto.socket.SocketInsertNotificationDTO;
import aldentebackend.dto.socket.SocketInfoNotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Controller
public class WebSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public SocketInfoNotificationDTO send(SocketInsertNotificationDTO socketInsertNotificationDTO, Principal principal, @Header("simpSessionId") String sessionId) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        var payload = new SocketInfoNotificationDTO("FROM", "TO", "ee", time);

        String dest = "/topic/specific-user-user" + sessionId;
        simpMessagingTemplate.convertAndSend(dest, payload);
        return new SocketInfoNotificationDTO(socketInsertNotificationDTO.getFrom(), sessionId, socketInsertNotificationDTO.getText() + sessionId, time);
    }

}
