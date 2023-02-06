package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.OrderItem;
import aldentebackend.repository.OrderItemRepository;
import aldentebackend.service.ReportService;
import aldentebackend.support.menuitem.MenuItemToMenuItemInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final OrderItemRepository repository;
    private final MenuItemToMenuItemInfoDTO toMenuItemInfoDTO;

    @Override
    public Map<Long, Map<String, Object>> getReportForEveryItem(LocalDate start, LocalDate end) {
        if(start == null || end == null)
            throw new BadRequestException("Dates can't be null");
        if(start.compareTo(end) >= 0)
            throw new BadRequestException("Start date is not before end date");
        if(start.compareTo(LocalDate.now()) >= 0 || end.compareTo(LocalDate.now()) >= 0)
            throw new BadRequestException("Start/end date is in the future");

        Collection<OrderItem> orderItems = repository.findAllInRange(start.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000, end.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC) * 1000);

        Map<Long, Map<String, Object>> map = new HashMap<>();

        for(OrderItem item : orderItems) {
            Long id = item.getMenuItem().getId();
            if (!map.containsKey(id)) {
                Map<String, Object> defaultMap = new HashMap<>();
                defaultMap.put("income", 0.0);
                defaultMap.put("expense", 0.0);
                defaultMap.put("item", toMenuItemInfoDTO.convert(item.getMenuItem()));

                map.put(id, defaultMap);
            }

            double income = (double) map.get(id).get("income") + item.getCurrentPrice();
            double expense = (double) map.get(id).get("expense") + item.getCurrentExpense();

            map.get(id).put("income", income);
            map.get(id).put("expense", expense);

            map.get(id);
        }

        return map;
    }
}
