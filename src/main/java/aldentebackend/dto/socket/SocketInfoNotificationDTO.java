package aldentebackend.dto.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocketInfoNotificationDTO {
    String from;
    String to;
    String message;
    String time;
    String toRole;

    public SocketInfoNotificationDTO() {
    }

    public SocketInfoNotificationDTO(String from, String to, String message, String time) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.time = time;
    }

}
