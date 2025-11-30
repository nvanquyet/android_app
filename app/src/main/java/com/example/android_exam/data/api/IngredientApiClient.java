package com.example.android_exam.data.api;

import android.util.Log;

import com.example.android_exam.core.config.AppConfig;
import com.example.android_exam.data.dto.ingredient.CreateIngredientRequestDto;
import com.example.android_exam.data.dto.ingredient.DeleteIngredientRequestDto;
import com.example.android_exam.data.dto.ingredient.IngredientDataResponseDto;
import com.example.android_exam.data.dto.ingredient.IngredientFilterDto;
import com.example.android_exam.data.dto.ingredient.IngredientSearchResultDto;
import com.example.android_exam.data.dto.ingredient.UpdateIngredientRequestDto;
import com.example.android_exam.data.dto.response.ApiResponse;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class IngredientApiClient extends BaseApiClient {

    // Tạo ingredient chỉ với JSON (không có file)
    public void createIngredient(CreateIngredientRequestDto dto, DataCallback<ApiResponse<IngredientDataResponseDto>> callback) {
        createIngredient(dto, null, callback);
    }

    // Tạo ingredient với file
    public void createIngredient(CreateIngredientRequestDto dto, File imageFile, DataCallback<ApiResponse<IngredientDataResponseDto>> callback) {
        Map<String, String> formFields = createFormFieldsFromDto(dto);

        postMultipart(
                AppConfig.Endpoints.INGREDIENT_CREATE,
                formFields,
                imageFile,
                "Image",
                new TypeToken<ApiResponse<IngredientDataResponseDto>>() {},
                callback
        );
    }

    // Update ingredient chỉ với JSON
    public void updateIngredient(UpdateIngredientRequestDto dto, DataCallback<ApiResponse<IngredientDataResponseDto>> callback) {
        updateIngredient(dto, null, callback);
    }

    // Update ingredient với file
    public void updateIngredient(UpdateIngredientRequestDto dto, File imageFile, DataCallback<ApiResponse<IngredientDataResponseDto>> callback) {
        if (dto.getId() == null) {
            callback.onError("Ingredient ID is required for update");
            return;
        }
        
        Map<String, String> formFields = createFormFieldsFromDto(dto);
        // Remove Id from form fields as it's in the path
        formFields.remove("Id");
        
        String endpoint = String.format(AppConfig.Endpoints.INGREDIENT_UPDATE, dto.getId());
        putMultipart(
                endpoint,
                formFields,
                imageFile,
                "Image",
                new TypeToken<ApiResponse<IngredientDataResponseDto>>() {},
                callback
        );
    }

    // Delete ingredient - id in path, body is optional according to API doc
    public void deleteIngredient(int id, DataCallback<ApiResponse<Boolean>> callback) {
        String endpoint = String.format(AppConfig.Endpoints.INGREDIENT_DELETE, id);
        // DELETE with path parameter, body contains id (as per API doc)
        DeleteIngredientRequestDto dto = new DeleteIngredientRequestDto();
        dto.setId(id);
        delete(endpoint, dto, new TypeToken<ApiResponse<Boolean>>(){}, callback);
    }

    // Get ingredient by ID
    public void getIngredientById(int id, DataCallback<ApiResponse<IngredientDataResponseDto>> callback) {
        get(String.format(AppConfig.Endpoints.INGREDIENT_BY_ID, id), new TypeToken<ApiResponse<IngredientDataResponseDto>>(){}, callback);
    }

    // Get all ingredients với filter - matches API documentation
    public void getAllIngredients(IngredientFilterDto filter, DataCallback<ApiResponse<IngredientSearchResultDto>> callback) {
        // Build query parameters according to API doc
        HttpUrl.Builder urlBuilder = HttpUrl.parse(AppConfig.BASE_URL + AppConfig.Endpoints.INGREDIENT_LIST).newBuilder();

        if (filter.getPage() != null) {
            urlBuilder.addQueryParameter("page", filter.getPage().toString());
        }
        if (filter.getPageSize() != null) {
            urlBuilder.addQueryParameter("pageSize", filter.getPageSize().toString());
        }
        if (filter.getCategory() != null) {
            urlBuilder.addQueryParameter("category", filter.getCategory().toApiName());
        }
        if (filter.getSearch() != null && !filter.getSearch().trim().isEmpty()) {
            urlBuilder.addQueryParameter("search", filter.getSearch().trim());
        }
        if (filter.getExpiryDateFrom() != null) {
            // Format date to ISO 8601 string for query parameter
            String dateFrom = com.example.android_exam.core.datetime.DateTimeManager.getInstance()
                    .formatDateTimeToIsoUTC(filter.getExpiryDateFrom());
            urlBuilder.addQueryParameter("expiryDateFrom", dateFrom);
        }
        if (filter.getExpiryDateTo() != null) {
            // Format date to ISO 8601 string for query parameter
            String dateTo = com.example.android_exam.core.datetime.DateTimeManager.getInstance()
                    .formatDateTimeToIsoUTC(filter.getExpiryDateTo());
            urlBuilder.addQueryParameter("expiryDateTo", dateTo);
        }

        HttpUrl finalUrl = urlBuilder.build();
        Log.d("IngredientApiClient", "Query URL: " + finalUrl.toString());

        Request request = createRequestBuilder("")
                .url(finalUrl)
                .get()
                .build();

        executeRequest(request, new TypeToken<ApiResponse<IngredientSearchResultDto>>(){}, callback);
    }

    // Utility method để tạo form fields từ CreateIngredientRequestDto
    // Unit and Category must be string enum names (e.g., "Gram", "Vegetables") per API doc
    private Map<String, String> createFormFieldsFromDto(CreateIngredientRequestDto dto) {
        Map<String, String> formFields = new HashMap<>();

        if (dto.getName() != null) {
            formFields.put("Name", dto.getName());
        }
        if (dto.getDescription() != null) {
            formFields.put("Description", dto.getDescription());
        }
        if (dto.getQuantity() != null) {
            formFields.put("Quantity", dto.getQuantity().toString());
        }
        if (dto.getUnit() != null) {
            // API expects string enum name (e.g., "Gram"), not integer
            formFields.put("Unit", dto.getUnit().toApiName());
        }
        if (dto.getCategory() != null) {
            // API expects string enum name (e.g., "Vegetables"), not integer
            formFields.put("Category", dto.getCategory().toApiName());
        }
        if (dto.getExpiryDate() != null) {
            formFields.put("ExpiryDate", dto.getExpiryDate());
        }

        return formFields;
    }

    // Utility method để tạo form fields từ UpdateIngredientRequestDto
    // Note: Id is in path, not in form fields
    private Map<String, String> createFormFieldsFromDto(UpdateIngredientRequestDto dto) {
        Map<String, String> formFields = new HashMap<>();

        if (dto.getName() != null) {
            formFields.put("Name", dto.getName());
        }
        if (dto.getDescription() != null) {
            formFields.put("Description", dto.getDescription());
        }
        if (dto.getQuantity() != null) {
            formFields.put("Quantity", dto.getQuantity().toString());
        }
        if (dto.getUnit() != null) {
            // API expects string enum name (e.g., "Gram"), not integer
            formFields.put("Unit", dto.getUnit().toApiName());
        }
        if (dto.getCategory() != null) {
            // API expects string enum name (e.g., "Vegetables"), not integer
            formFields.put("Category", dto.getCategory().toApiName());
        }
        if (dto.getExpiryDate() != null) {
            formFields.put("ExpiryDate", dto.getExpiryDate());
        }

        return formFields;
    }
}