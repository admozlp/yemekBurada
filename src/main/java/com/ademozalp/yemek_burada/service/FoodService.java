package com.ademozalp.yemek_burada.service;


import com.ademozalp.yemek_burada.client.firebase.ImageService;
import com.ademozalp.yemek_burada.dto.food.request.CreateFoodRequest;
import com.ademozalp.yemek_burada.dto.food.request.SearchFoodRequest;
import com.ademozalp.yemek_burada.dto.food.response.FoodResponse;
import com.ademozalp.yemek_burada.dto.food.response.FoodSearchResponse;
import com.ademozalp.yemek_burada.exception.YemekBuradaException;
import com.ademozalp.yemek_burada.model.Food;
import com.ademozalp.yemek_burada.model.enums.FoodStatus;
import com.ademozalp.yemek_burada.repository.FoodRepository;
import com.ademozalp.yemek_burada.repository.specification.FoodSpecification;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class FoodService {
    private final FoodRepository repository;
    private final ImageService imageService;

    public FoodService(FoodRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }


    public FoodResponse createFood(CreateFoodRequest request) {
        Food food = CreateFoodRequest.convert(request);

        if (request.getPhoto() != null) {
            String photoUrl = imageService.upload(request.getPhoto());
            food.setPhotoUrl(photoUrl);
        }

        Food savedFood = repository.save(food);

        return FoodResponse.convert(savedFood);
    }

    @Transactional(rollbackOn = SQLException.class)
    public List<FoodResponse> createBulkFood(List<CreateFoodRequest> request) {
        List<Food> foodList = request.stream()
                .map(CreateFoodRequest::convert)
                .map(repository::save)
                .toList();

        return foodList.stream().map(FoodResponse::convert).toList();
    }

    public FoodSearchResponse getAllFoods(SearchFoodRequest request) {
        PageRequest pageRequest = getPageRequest(request);

        Page<Food> page = repository.findAll(pageRequest);

        List<FoodResponse> foodResponseList = page.stream().map(FoodResponse::convert).toList();

        return new FoodSearchResponse(page.getTotalPages(), page.getNumber(), page.getTotalElements(), foodResponseList);
    }

    public FoodResponse getFoodById(Long id) {
        Food food = repository.findById(id)
                .orElseThrow(() -> new YemekBuradaException("Yemek bulunamadı!"));

        return FoodResponse.convert(food);
    }

    // page ve size göre sayfalama, sortDirection ve sortBy'a göre sıralama, name ve status'e göre filtreleme yapar.
    public FoodSearchResponse getFoodsByFilter(SearchFoodRequest request) {
        PageRequest pageRequest = getPageRequest(request);

        Specification<Food> spec = FoodSpecification.filter(request);

        Page<Food> page = repository.findAll(spec, pageRequest);

        List<FoodResponse> foodResponseList = page.stream().map(FoodResponse::convert).toList();

        return new FoodSearchResponse(page.getTotalPages(), page.getNumber(), page.getTotalElements(), foodResponseList);
    }


    @NotNull
    private static PageRequest getPageRequest(SearchFoodRequest request) {
        return PageRequest.of(request.getPage() == null ? 0 : request.getPage(), request.getSize() == null ? 10 : request.getSize())
                .withSort(Sort.by(Objects.equals(Objects.requireNonNull(request.getSortDirection()).toUpperCase(), "DESC") ?
                                Sort.Direction.DESC : Sort.Direction.ASC,
                        request.getSortBy()));
    }


    protected Food getFoodByIdAndStatusAvailableForOtherService(Long id) {
        return repository.findByIdAndStatus(id, FoodStatus.AVAILABLE)
                .orElseThrow(() -> new YemekBuradaException("Yemek bulunamadı!"));
    }


}
