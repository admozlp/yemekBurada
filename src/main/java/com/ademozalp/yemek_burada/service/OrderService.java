package com.ademozalp.yemek_burada.service;

import com.ademozalp.yemek_burada.dto.order.request.CreateOrderRequest;
import com.ademozalp.yemek_burada.dto.order.request.FilterOrderRequest;
import com.ademozalp.yemek_burada.dto.order.response.ActiveOrderResponse;
import com.ademozalp.yemek_burada.dto.order.response.OrderResponse;
import com.ademozalp.yemek_burada.exception.YemekBuradaException;
import com.ademozalp.yemek_burada.model.Desk;
import com.ademozalp.yemek_burada.model.Food;
import com.ademozalp.yemek_burada.model.Order;
import com.ademozalp.yemek_burada.model.enums.OrderStatus;
import com.ademozalp.yemek_burada.repository.OrderRepository;
import com.ademozalp.yemek_burada.repository.specification.OrderSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final FoodService foodService;
    private final DeskService deskService;

    public OrderService(OrderRepository repository, FoodService foodService, DeskService deskService) {
        this.repository = repository;
        this.foodService = foodService;
        this.deskService = deskService;
    }


    public OrderResponse createOrder(CreateOrderRequest request) {
        List<Food> foods = request.getFoodIds().stream().map(foodService::getFoodByIdAndStatusAvailableForOtherService).toList();

        Desk desk = deskService.getDeskByIdForOtherService(request.getDeskId());

        Order order = CreateOrderRequest.convert(request, foods, desk);

        Order savedOrder = repository.save(order);

        return OrderResponse.convert(savedOrder);
    }

    // response no details;
    // status PREPARING ve READY olan siparişler aktif siparişler olarak kabul edilir.
    public List<ActiveOrderResponse> getActiveOrders(FilterOrderRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        List<Order> orders = repository.findAllByStatusIn(List.of(OrderStatus.PREPARING, OrderStatus.READY), pageRequest);

        return orders.stream().map(ActiveOrderResponse::convert).toList();
    }

    public OrderResponse changeOrderStatus(String status, Long orderId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new YemekBuradaException("Sipariş bulunamadı"));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));

        Order updatedOrder = repository.save(order);

        return OrderResponse.convert(updatedOrder);
    }

    // with details, optional filterBy createdDate
    public List<OrderResponse> getAllOrdersByCreatedDate(FilterOrderRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        Specification<Order> spec = OrderSpecification.createdDateBetween(request);

        List<Order> orders = repository.findAll(spec, pageRequest).getContent();

        return orders.stream().map(OrderResponse::convert).toList();
    }


}
