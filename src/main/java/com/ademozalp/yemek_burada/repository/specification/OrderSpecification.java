package com.ademozalp.yemek_burada.repository.specification;

import com.ademozalp.yemek_burada.dto.order.request.FilterOrderRequest;
import com.ademozalp.yemek_burada.model.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecification {
    private OrderSpecification() {
    }

    public static Specification<Order> createdDateBetween(FilterOrderRequest request) {

        Specification<Order> spec = Specification.where(null);

        if (request.getStartDate() != null) {
            LocalDateTime startDate = LocalDateTime.parse(request.getStartDate());
            spec = spec.and(createdDateAfter(startDate));
        }

        if (request.getEndDate() != null) {
            LocalDateTime endDate = LocalDateTime.parse(request.getEndDate());
            spec = spec.and(createdDateBefore(endDate));
        }

        return spec;
    }

    private static Specification<Order> createdDateAfter(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("cratedDateTime"), startDate);
    }

    // Specification for filtering by created date before the end date
    private static Specification<Order> createdDateBefore(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("cratedDateTime"), endDate);
    }
}
