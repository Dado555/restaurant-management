package aldentebackend.support.order;

import aldentebackend.dto.order.OrderInfoDTO;
import aldentebackend.model.Order;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderInfoDTO extends AbstractConverter<Order, OrderInfoDTO> {

    @Override
    public OrderInfoDTO convert(@NonNull Order source) {
        return getModelMapper().map(source, OrderInfoDTO.class);
    }
}
