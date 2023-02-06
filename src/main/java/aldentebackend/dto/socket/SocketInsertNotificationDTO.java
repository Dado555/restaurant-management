package aldentebackend.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SocketInsertNotificationDTO {
    private String from;
    private String text;
}
