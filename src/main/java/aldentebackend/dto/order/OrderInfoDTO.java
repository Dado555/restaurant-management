package aldentebackend.dto.order;

import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.dto.user.UserInfoDTO;
import aldentebackend.model.enums.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderInfoDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long date;

    @NotNull
    private TableInfoDTO table;

    @NotNull
    private UserInfoDTO waiter;

    @NotNull
    private OrderStatus status;
}
