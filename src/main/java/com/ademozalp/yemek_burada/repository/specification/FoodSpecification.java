package com.ademozalp.yemek_burada.repository.specification;


import com.ademozalp.yemek_burada.dto.food.request.SearchFoodRequest;
import com.ademozalp.yemek_burada.model.Food;
import org.springframework.data.jpa.domain.Specification;

public class FoodSpecification {
    // bu sınıfın hiç bir zaman nesnesini oluşturmayı düşünmüyorum. sadece static alanlara(methodlara) eişmek istiyorum.
    // ileride diğer geliştiricler bu sınıfın nesnesini oluşturmaması için private constructor ekledim
    private FoodSpecification() {
    }


    public static Specification<Food> filter(SearchFoodRequest request) {
        Specification<Food> spec = Specification.where(null);

        if (request.getName() != null) {
            spec = spec.and(foodNameContains(request.getName()));
        }
        if (request.getStatus() != null) {
            spec = spec.and(foodStatusEqual(request.getStatus()));
        }

        return spec;
    }

    private static Specification<Food> foodNameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<Food> foodStatusEqual(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status.toUpperCase());
    }


}
