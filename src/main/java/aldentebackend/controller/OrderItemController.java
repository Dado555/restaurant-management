package aldentebackend.controller;

import aldentebackend.dto.orderitem.OrderItemCreateDTO;
import aldentebackend.dto.orderitem.OrderItemInfoDTO;
import aldentebackend.dto.orderitem.OrderItemUpdateDTO;
import aldentebackend.model.OrderItem;
import aldentebackend.service.OrderItemService;
import aldentebackend.support.IConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    private final IConverter<OrderItemCreateDTO, OrderItem> toOrderItem;
    private final IConverter<OrderItem, OrderItemInfoDTO> toOrderItemInfoDTO;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItemInfoDTO>> getOrderItems() {
        List<OrderItem> orderItems = orderItemService.findAll();
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItems), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> getOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.findOne(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> createOrderItem(@Valid @RequestBody OrderItemCreateDTO orderItemCreateDTO) {
        OrderItem orderItem = orderItemService.create(toOrderItem.convert(orderItemCreateDTO));
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping(value = "/{id}/awaiting-approval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> markOrderItemAsAwaitingApproval(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.markOrderItemAsAwaitingApproval(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping(value = "/{id}/for-preparation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> markOrderItemAsForPreparation(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.markOrderItemAsForPreparation(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping(value = "/{id}/canceled", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> cancelOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.cancelOrderItem(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('COOK, BARTENDER')")
    @PutMapping(value = "/{id}/in-progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> takeOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.takeOrderItem(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('COOK, BARTENDER')")
    @PutMapping(value = "/{id}/ready", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> markOrderItemAsReady(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.markOrderItemAsReady(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping(value = "/{id}/delivered", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemInfoDTO> markOrderItemAsDelivered(@PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.markOrderItemAsDelivered(id);
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WAITER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderItemInfoDTO> updateOrderItem(@PathVariable("id") Long id,
                                                            @Valid @RequestBody OrderItemUpdateDTO newItem) {
        OrderItem orderItem = orderItemService.update(id, newItem.getAmount(), newItem.getDescription());
        return new ResponseEntity<>(toOrderItemInfoDTO.convert(orderItem), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'WAITER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable("id") Long id) {
        orderItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/profit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getProfit() {
        return new ResponseEntity<>(orderItemService.getProfit(), HttpStatus.OK);
    }
}
