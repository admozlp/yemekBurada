package com.ademozalp.yemek_burada.controller;


import com.ademozalp.yemek_burada.dto.GenericResponse;
import com.ademozalp.yemek_burada.dto.food.request.CreateFoodRequest;
import com.ademozalp.yemek_burada.dto.food.request.SearchFoodRequest;
import com.ademozalp.yemek_burada.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public GenericResponse createFood(@ModelAttribute @Valid CreateFoodRequest request) {
        return new GenericResponse("Yemek başarıyla oluşturuldu!", 201, foodService.createFood(request));
    }

    @PostMapping("/bulk-create")
    public GenericResponse createBulkFood(@RequestBody @Valid List<CreateFoodRequest> request) {
        return new GenericResponse("Yemekler başarıyla oluşturuldu!", 200, foodService.createBulkFood(request));
    }

    @GetMapping
    public GenericResponse getAllFoods(@RequestBody @Valid SearchFoodRequest request) {
        return new GenericResponse("Yemekler başarıyla listelendi!", 200, foodService.getAllFoods(request));
    }

    @GetMapping("/{id}")
    public GenericResponse getFoodById(@PathVariable Long id) {
        return new GenericResponse("Yemek başarıyla getirildi!", 200, foodService.getFoodById(id));
    }

    @GetMapping("/filter")
    public GenericResponse getFoodByFilter(@RequestBody @Valid SearchFoodRequest request) {
        return new GenericResponse("Yemekler başarıyla listelendi!", 200, foodService.getFoodsByFilter(request));
    }
}
