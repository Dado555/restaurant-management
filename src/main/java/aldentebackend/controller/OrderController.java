package aldentebackend.controller;

import aldentebackend.dto.order.OrderCreateDTO;
import aldentebackend.dto.order.OrderInfoDTO;
import aldentebackend.dto.order.OrderWithOrderItemsDTO;
import aldentebackend.dto.orderitem.OrderItemInfoDTO;
import aldentebackend.model.*;

import aldentebackend.service.*;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    private final IConverter<OrderCreateDTO, Order> toOrder;
    private final IConverter<Order, OrderInfoDTO> toOrderInfoDTO;
    private final IConverter<Order, OrderWithOrderItemsDTO> toOrderWithOrderItemsDTO;

    private final IConverter<OrderItem, OrderItemInfoDTO> toOrderItemInfoDTO;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderInfoDTO>> getAllOrders()  {
        Collection<Order> orders = orderService.findAll();
        return new ResponseEntity<>(toOrderInfoDTO.convert(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getOrdersCount()  {
        return new ResponseEntity<>(orderService.getOrdersCount(), HttpStatus.OK);
    }

    @GetMapping(value = "/new-orders-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getNewOrdersCount()  {
        return new ResponseEntity<>(orderService.getNewOrdersCount(), HttpStatus.OK);
    }

    @GetMapping(value = "/{days}/count-last-Ndays", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer[]> getNewOrdersCountForNDays(@PathVariable("days") Integer days)  {
        return new ResponseEntity<>(orderService.getOrdersCountForNDays(days), HttpStatus.OK);
    }

    @GetMapping(value = "/{days}/income-and-expense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Collection<Collection<Double>>>> getIncomeAndExpense(@PathVariable("days") Integer days)  {
        return new ResponseEntity<>(orderItemService.getIncomesAndExpensesForNDays(days), HttpStatus.OK);
    }

    @GetMapping(value = "/{days}/orders-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Integer>> getOrdersCountForNDays(@PathVariable("days") Integer days)  {
        return new ResponseEntity<>(orderService.getOrdersCountForLastNDays(days), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @GetMapping(value = "active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderInfoDTO>> getAllActiveOrders()  {
        Collection<Order> orders = orderService.findAllActiveOrders();
        return new ResponseEntity<>(toOrderInfoDTO.convert(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderInfoDTO> getOneOrder(@PathVariable("id") Long id)  {
        Order order = orderService.findOne(id);
        return new ResponseEntity<>(toOrderInfoDTO.convert(order), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/order-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItemInfoDTO>> getOrderItemsForOrder(@PathVariable("id") Long id)  {
        Collection<OrderItem> orderItems = orderService.findOrderItemsForOrder(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItems), HttpStatus.OK);
    }

    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderWithOrderItemsDTO>> getNewOrders()  {
        Collection<Order> orders = orderItemService.findNewOrderItemsGroupedInOrders();
        return new ResponseEntity<>(toOrderWithOrderItemsDTO.convert(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderWithOrderItemsDTO>> getAcceptedOrders()  {
        Collection<Order> orders = orderItemService.findAcceptedOrderItemsGroupedInOrders();
        return new ResponseEntity<>(toOrderWithOrderItemsDTO.convert(orders), HttpStatus.OK);
    }

    @GetMapping(value = "/table/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderWithOrderItemsDTO>> getOrdersForTable(@PathVariable("id") Long id)  {
        Collection<Order> orders = orderService.findOrdersForTable(id);
        return new ResponseEntity<>(toOrderWithOrderItemsDTO.convert(orders), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderInfoDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO)  {
        Order order = orderService.create(toOrder.convert(orderCreateDTO));
        return new ResponseEntity<>(toOrderInfoDTO.convert(order), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('WAITER')")
    @PostMapping(value = "/{id}/checkout")
    public ResponseEntity<OrderInfoDTO> checkoutOrder(@PathVariable("id") Long id) {
        Order order = orderService.checkoutOrder(id);
        return new ResponseEntity<>(toOrderInfoDTO.convert(order), HttpStatus.OK);
    }
}
