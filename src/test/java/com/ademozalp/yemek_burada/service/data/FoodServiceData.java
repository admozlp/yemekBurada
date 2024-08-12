package com.ademozalp.yemek_burada.service.data;

import com.ademozalp.yemek_burada.dto.food.request.CreateFoodRequest;
import com.ademozalp.yemek_burada.dto.food.request.SearchFoodRequest;
import com.ademozalp.yemek_burada.dto.food.response.FoodResponse;
import com.ademozalp.yemek_burada.dto.food.response.FoodSearchResponse;
import com.ademozalp.yemek_burada.model.Food;
import com.ademozalp.yemek_burada.model.enums.FoodStatus;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class FoodServiceData {

    public static final String PHOTO_URL = "photoUrl";

    // create and bulkCreate
    public static final CreateFoodRequest createFoodRequest = new CreateFoodRequest("food1", "description1", "10.2", 10, new MockMultipartFile("photo", new byte[0]));
    public static final Food food = new Food(0L, Objects.requireNonNull(createFoodRequest.getName()),
            new BigDecimal(Objects.requireNonNull(createFoodRequest.getPrice())), FoodStatus.AVAILABLE);

    public static final FoodResponse expectedFoodResponse =
            new FoodResponse(0L, createFoodRequest.getName(), createFoodRequest.getDescription(), createFoodRequest.getPrice(), PHOTO_URL,
                    createFoodRequest.getReadinessTime(), FoodStatus.AVAILABLE.toString());

    public static final List<CreateFoodRequest> createFoodRequestList = List.of(createFoodRequest, createFoodRequest);
    public static final List<Food> foodList = List.of(food, food);
    public static final List<FoodResponse> expectedList = List.of(expectedFoodResponse, expectedFoodResponse);


    // getAllFoods
    public static final SearchFoodRequest searchFoodRequest = new SearchFoodRequest(0, 10);

    public static final AbstractPageRequest pageRequest = PageRequest.of(searchFoodRequest.getPage() == null
                    ? 0 : searchFoodRequest.getPage(), searchFoodRequest.getSize() == null ? 10 : searchFoodRequest.getSize())
            .withSort(Sort.by(Objects.equals(Objects.requireNonNull(searchFoodRequest.getSortDirection()).toUpperCase(), "DESC") ?
                            Sort.Direction.DESC : Sort.Direction.ASC,
                    searchFoodRequest.getSortBy()));


    public static final Food searchFood = new Food(0L, "food1",
            new BigDecimal("32"), FoodStatus.AVAILABLE);

    public static final Page<Food> page = new PageImpl<>(List.of(searchFood), pageRequest, 0);

    public static final FoodResponse expectedFoodResponseForSearch =
            new FoodResponse(0L, searchFood.getName(), searchFood.getDescription(), searchFood.getPrice().toString(), PHOTO_URL,
                    searchFood.getReadinessTime(), searchFood.getStatus().toString());

    public static final List<FoodResponse> foodResponseList = List.of(expectedFoodResponseForSearch);

    public static final FoodSearchResponse expectedFoodSearchResponse = new FoodSearchResponse(1, 0, 1, foodResponseList);


    // getById
    public static final Long FOOD_ID = 1L;
    public static final Food foodById = new Food(FOOD_ID, "food2",
            new BigDecimal("45"), FoodStatus.AVAILABLE);

    public static final FoodResponse expectedFoodResponseForById =
            new FoodResponse(FOOD_ID, foodById.getName(), foodById.getDescription(), foodById.getPrice().toString(), PHOTO_URL,
                    foodById.getReadinessTime(), foodById.getStatus().toString());


    // getFoodsByFilter
    public static final SearchFoodRequest searchFoodRequestForFilter =
            new SearchFoodRequest(0, 20, "name", "ASC", "food1", "AVAILABLE");

    public static final Food filterFood = new Food(0L, "food1",
            new BigDecimal("45"), FoodStatus.AVAILABLE);

    public static final AbstractPageRequest pageRequestForFilter = PageRequest.of(searchFoodRequestForFilter.getPage() == null
                    ? 0 : searchFoodRequestForFilter.getPage(), searchFoodRequestForFilter.getSize() == null ? 10 : searchFoodRequestForFilter.getSize())
            .withSort(Sort.by(Objects.equals(Objects.requireNonNull(searchFoodRequestForFilter.getSortDirection()).toUpperCase(), "DESC") ?
                            Sort.Direction.DESC : Sort.Direction.ASC,
                    searchFoodRequestForFilter.getSortBy()));


    public static final Page<Food> pageForFilter = new PageImpl<>(List.of(filterFood), pageRequestForFilter, 1);

    public static final FoodResponse expectedFoodResponseForFilter =
            new FoodResponse(0L, searchFood.getName(), searchFood.getDescription(), searchFood.getPrice().toString(), PHOTO_URL,
                    searchFood.getReadinessTime(), searchFood.getStatus().toString());

    public static final List<FoodResponse> foodResponseListForFilter = List.of(expectedFoodResponseForFilter);

    public static final FoodSearchResponse expectedFoodSearchResponseForFilter = new FoodSearchResponse(1, 0, 1, foodResponseListForFilter);
}
