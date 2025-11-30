package com.example.android_exam.data.api;

import com.example.android_exam.core.config.AppConfig;
import com.example.android_exam.data.dto.food.FoodAnalysticResponseDto;
import com.example.android_exam.data.dto.ingredient.IngredientAnalysticResponseDto;
import com.example.android_exam.data.dto.response.ApiResponse;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class AiClient extends BaseApiClient {

    /**
     * Gọi API để phát hiện món ăn từ ảnh
     * Endpoint: POST api/ai/detect-food (multipart)
     */
    public void detectFood(File imageFile, ResponseCallback<ApiResponse<FoodAnalysticResponseDto>> callback) {
        Map<String, String> formFields = Collections.emptyMap();
        postMultipart(
                AppConfig.Endpoints.AI_DETECT_FOOD,
                formFields,
                imageFile,
                "Image",
                new TypeToken<ApiResponse<FoodAnalysticResponseDto>>() {},
                callback
        );
    }

    /**
     * Gọi API để phân tích nguyên liệu từ ảnh
     * Endpoint: POST api/ai/detect-ingredient (multipart)
     */
    public void detectIngredient(File imageFile, ResponseCallback<ApiResponse<IngredientAnalysticResponseDto>> callback) {
        Map<String, String> formFields = Collections.emptyMap();
        postMultipart(
                AppConfig.Endpoints.AI_DETECT_INGREDIENT,
                formFields,
                imageFile,
                "Image",
                new TypeToken<ApiResponse<IngredientAnalysticResponseDto>>() {},
                callback
        );
    }


}
