package com.irfanacode.dreamshops.service.order;

import com.irfanacode.dreamshops.dto.OrderDto;
import com.irfanacode.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto  convertToDto(Order order);
}
