package aldentebackend.service.socket;

import aldentebackend.dto.socket.SocketInfoNotificationDTO;
import aldentebackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public SocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void notify(User from, User to, String message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        SocketInfoNotificationDTO socketInfoNotificationDTO = new SocketInfoNotificationDTO(from.getFirstName(), to.getFirstName(), message, time);
        simpMessagingTemplate.convertAndSend("/topic/notification", socketInfoNotificationDTO); // Spring will automatically prepend "/user/" to destination => "/user/notify"
        return;
    }
    public void notify(String from, String to, String message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        SocketInfoNotificationDTO socketInfoNotificationDTO = new SocketInfoNotificationDTO(from, to, message, time);
        simpMessagingTemplate.convertAndSend("/topic/notification", socketInfoNotificationDTO); // Spring will automatically prepend "/user/" to destination => "/user/notify"
        simpMessagingTemplate.convertAndSendToUser(
                to, "/topic/specific-user", new SocketInfoNotificationDTO(from, to, message, time));
        return;

    }

    public void notify(String from, String to, String message, String forRole) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        SocketInfoNotificationDTO socketInfoNotificationDTO = new SocketInfoNotificationDTO(from, to, message, time, forRole);
        simpMessagingTemplate.convertAndSend("/topic/notification", socketInfoNotificationDTO); // Spring will automatically prepend "/user/" to destination => "/user/notify"
        simpMessagingTemplate.convertAndSendToUser(
                to, "/topic/specific-user", new SocketInfoNotificationDTO(from, to, message, time, forRole));
        return;

    }

    public void notifyFormMe(String to, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return;

        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User from_user = (org.springframework.security.core.userdetails.User) principal;
            notify(from_user.getUsername(), to, message);
        }
    }

    public void notifyFormMe(String to, String message, String forRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return;

        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User from_user = (org.springframework.security.core.userdetails.User) principal;
            notify(from_user.getUsername(), to, message, forRole);
        }
    }
}
