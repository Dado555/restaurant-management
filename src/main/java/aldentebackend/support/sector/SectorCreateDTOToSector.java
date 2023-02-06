package aldentebackend.support.sector;

import aldentebackend.dto.order.OrderCreateDTO;
import aldentebackend.dto.sector.SectorCreateDTO;
import aldentebackend.model.Order;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import aldentebackend.model.Waiter;
import aldentebackend.model.enums.OrderStatus;
import aldentebackend.service.TableService;
import aldentebackend.service.UserService;
import aldentebackend.support.AbstractConverter;
import aldentebackend.support.orderitem.OrderItemInOrderCreateDTOToOrderItem;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SectorCreateDTOToSector extends AbstractConverter<SectorCreateDTO, Sector> {

    @Override
    public Sector convert(@NonNull SectorCreateDTO source) {

        Sector sector = new Sector();
        sector.setName(source.getName());
        return sector;
    }
}
