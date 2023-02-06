package aldentebackend.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    FOR_PREPARATION(0),
    IN_PROGRESS(1),
    READY(2),
    DELIVERED(3),
    FINISHED(4);

    private final Integer type;

    OrderStatus(Integer type) {
        this.type = type;
    }

}
