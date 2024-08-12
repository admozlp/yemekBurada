package com.ademozalp.yemek_burada.controller;


import com.ademozalp.yemek_burada.dto.GenericResponse;
import com.ademozalp.yemek_burada.dto.order.request.CreateOrderRequest;
import com.ademozalp.yemek_burada.dto.order.request.FilterOrderRequest;
import com.ademozalp.yemek_burada.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public GenericResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        return new GenericResponse("Sipariş oluşturuldu", 201, orderService.createOrder(request));
    }

    @GetMapping("/actives")
    public GenericResponse getActiveOrders(@RequestBody @Valid FilterOrderRequest request) {
        return new GenericResponse("Aktif siparişler listelendi", 200, orderService.getActiveOrders(request));
    }

    @PutMapping("/{orderId}")
    public GenericResponse changeOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return new GenericResponse("Sipariş durumu değiştirildi", 200, orderService.changeOrderStatus(status, orderId));
    }

    @GetMapping("/filter")
    public GenericResponse getAllOrdersByCreatedDate(@RequestBody FilterOrderRequest request) {
        return new GenericResponse("Siparişler tarih aralığına göre listelendi", 200, orderService.getAllOrdersByCreatedDate(request));
    }


}
