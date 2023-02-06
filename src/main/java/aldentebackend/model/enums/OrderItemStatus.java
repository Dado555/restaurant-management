package aldentebackend.model.enums;

import lombok.Getter;

@Getter
public enum OrderItemStatus {

    AWAITING_APPROVAL(0),
    FOR_PREPARATION(1),
    CANCELED(2),
    IN_PROGRESS(3),
    READY(4),
    DELIVERED(5);

    private final Integer type;

    OrderItemStatus(Integer type) {
        this.type = type;
    }
}
