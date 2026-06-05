package com.example.SpringOnlineShop.controller;

import com.example.SpringOnlineShop.entity.Order;
import com.example.SpringOnlineShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placedOrder/{cartId}")
    public ResponseEntity<?> placeOrder(@PathVariable("cartId") int id){

        System.out.println("API HIT");

        Order order = orderService.placeOrder(id);
        return ResponseEntity.ok(order);


    }

    @GetMapping("/allOrder")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") int id){

        orderService.cancelOrder(id);

        Map<String,String> response = new HashMap<>();
        response.put("message","order canceled");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable("orderId") int id,
            @RequestParam String status) {

        orderService.updateStatus(id, status);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order status updated successfully");

        return ResponseEntity.ok(response);
    }
}
