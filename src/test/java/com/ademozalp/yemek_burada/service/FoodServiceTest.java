package com.ademozalp.yemek_burada.service;

import com.ademozalp.yemek_burada.client.firebase.ImageService;
import com.ademozalp.yemek_burada.dto.food.request.CreateFoodRequest;
import com.ademozalp.yemek_burada.dto.food.response.FoodResponse;
import com.ademozalp.yemek_burada.dto.food.response.FoodSearchResponse;
import com.ademozalp.yemek_burada.exception.YemekBuradaException;
import com.ademozalp.yemek_burada.model.Food;
import com.ademozalp.yemek_burada.repository.FoodRepository;
import com.ademozalp.yemek_burada.repository.specification.FoodSpecification;
import com.ademozalp.yemek_burada.service.data.FoodServiceData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoodServiceTest {
    @InjectMocks
    private FoodService underTest;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private ImageService imageService;

    private MockedStatic<CreateFoodRequest> createFoodRequestMockedStatic;
    private MockedStatic<FoodResponse> foodResponseMockedStatic;
    private MockedStatic<FoodSpecification> foodSpecificationMockedStatic;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        createFoodRequestMockedStatic = Mockito.mockStatic(CreateFoodRequest.class);
        foodResponseMockedStatic = Mockito.mockStatic(FoodResponse.class);
        foodSpecificationMockedStatic = Mockito.mockStatic(FoodSpecification.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
        createFoodRequestMockedStatic.close();
        foodResponseMockedStatic.close();
        foodSpecificationMockedStatic.close();
    }


    @DisplayName("Başarıyla bir yemek oluşturulmalı")
    @Test
    void createFoodTest() {
        createFoodRequestMockedStatic.when(() -> CreateFoodRequest.convert(FoodServiceData.createFoodRequest)).thenReturn(FoodServiceData.food);

        foodResponseMockedStatic.when(() -> FoodResponse.convert(FoodServiceData.food)).thenReturn(FoodServiceData.expectedFoodResponse);

        when(imageService.upload(FoodServiceData.createFoodRequest.getPhoto())).thenReturn(FoodServiceData.PHOTO_URL);

        when(foodRepository.save(any(Food.class))).thenReturn(FoodServiceData.food);

        FoodResponse actual = underTest.createFood(FoodServiceData.createFoodRequest);

        assertEquals(FoodServiceData.expectedFoodResponse, actual);

        verify(foodRepository, times(1)).save(FoodServiceData.food);
        createFoodRequestMockedStatic.verify(() -> CreateFoodRequest.convert(FoodServiceData.createFoodRequest), times(1));
        foodResponseMockedStatic.verify(() -> FoodResponse.convert(FoodServiceData.food), times(1));
    }

    @DisplayName("Yemeke listesini oluşturmalı ve döndürmeli")
    @Test
    void createBulkFoodTest() {
        createFoodRequestMockedStatic.when(() -> CreateFoodRequest.convert(FoodServiceData.createFoodRequest)).thenReturn(FoodServiceData.food);

        when(foodRepository.save(any(Food.class))).thenReturn(FoodServiceData.food);

        foodResponseMockedStatic.when(() -> FoodResponse.convert(FoodServiceData.food)).thenReturn(FoodServiceData.expectedFoodResponse);


        List<FoodResponse> actual = underTest.createBulkFood(FoodServiceData.createFoodRequestList);

        assertEquals(FoodServiceData.expectedList, actual);

        createFoodRequestMockedStatic.verify(() -> CreateFoodRequest.convert(FoodServiceData.createFoodRequest), times(2));
        verify(foodRepository, times(2)).save(FoodServiceData.food);
        foodResponseMockedStatic.verify(() -> FoodResponse.convert(FoodServiceData.food), times(2));
    }


    @DisplayName("Page ve size verildiğinde id göre artan sıralamsı beklenir")
    @Test
    void getAllFoodsTest() {
        when(foodRepository.findAll(FoodServiceData.pageRequest)).thenReturn(FoodServiceData.page);

        foodResponseMockedStatic.when(() -> FoodResponse.convert(FoodServiceData.searchFood)).thenReturn(FoodServiceData.expectedFoodResponseForSearch);

        FoodSearchResponse actual = underTest.getAllFoods(FoodServiceData.searchFoodRequest);

        assertEquals(FoodServiceData.expectedFoodSearchResponse, actual);

        verify(foodRepository, times(1)).findAll(FoodServiceData.pageRequest);
        foodResponseMockedStatic.verify(() -> FoodResponse.convert(FoodServiceData.searchFood), times(1));
    }

    @DisplayName("Mevuct olmayan bir yemek id'si verildiğinde Yemek bulunamadı hatası beklenir")
    @Test
    void getFoodByIdTest_shouldYemekBuradaException() {
        when(foodRepository.findById(FoodServiceData.FOOD_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getFoodById(0L))
                .isInstanceOf(YemekBuradaException.class)
                .hasMessage("Yemek bulunamadı!");

        verify(foodRepository, times(1)).findById(0L);
    }


    @DisplayName("Mevuct bir yemek id'si verildiğinde yemek döndürülmeli")
    @Test
    void getFoodByIdTest() {
        when(foodRepository.findById(FoodServiceData.FOOD_ID)).thenReturn(Optional.of(FoodServiceData.foodById));

        foodResponseMockedStatic.when(() -> FoodResponse.convert(FoodServiceData.foodById)).thenReturn(FoodServiceData.expectedFoodResponseForById);

        FoodResponse actual = underTest.getFoodById(FoodServiceData.FOOD_ID);

        assertEquals(FoodServiceData.expectedFoodResponseForById, actual);

        verify(foodRepository, times(1)).findById(FoodServiceData.FOOD_ID);
        foodResponseMockedStatic.verify(() -> FoodResponse.convert(FoodServiceData.foodById), times(1));
    }


    @DisplayName("name ve status verildiğinde and ile bağlanmış şekilde filtrelenmiş veriler gelmeli")
    @Test
    void getFoodsByFilterTest() {
        Specification spec = mock(Specification.class);

        foodSpecificationMockedStatic.when(() -> FoodSpecification.filter(FoodServiceData.searchFoodRequestForFilter))
                .thenReturn(spec);

        when(foodRepository.findAll(spec, FoodServiceData.pageRequestForFilter))
                .thenReturn(FoodServiceData.pageForFilter);

        foodResponseMockedStatic.when(() -> FoodResponse.convert(FoodServiceData.filterFood))
                .thenReturn(FoodServiceData.expectedFoodResponseForFilter);

        FoodSearchResponse actual = underTest.getFoodsByFilter(FoodServiceData.searchFoodRequestForFilter);

        assertEquals(FoodServiceData.expectedFoodSearchResponseForFilter, actual);

        foodSpecificationMockedStatic.verify(() -> FoodSpecification.filter(FoodServiceData.searchFoodRequestForFilter), times(1));
        verify(foodRepository, times(1)).findAll(spec, FoodServiceData.pageRequestForFilter);
        foodResponseMockedStatic.verify(() -> FoodResponse.convert(FoodServiceData.filterFood), times(1));
    }


}