package com.irfanacode.dreamshops.controller;

import com.irfanacode.dreamshops.dto.OrderDto;
import com.irfanacode.dreamshops.exceptions.ResourceNotFoundException;
import com.irfanacode.dreamshops.model.Order;
import com.irfanacode.dreamshops.response.ApiResponse;
import com.irfanacode.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrders(@RequestParam Long  userId) { // Implement this method
        try {
            Order  order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order placed successfully", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}/order") // Implement this method
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) { // Implement this method
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}/user") // Implement this method
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) { // Implement this method
        try {
           List<OrderDto>  orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
