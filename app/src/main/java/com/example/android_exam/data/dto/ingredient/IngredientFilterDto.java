package com.example.android_exam.data.dto.ingredient;

import com.example.android_exam.data.models.enums.IngredientCategory;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Filter DTO for GET /api/ingredient endpoint
 * Matches API documentation query parameters
 */
public class IngredientFilterDto {
    @SerializedName("page")
    private Integer page;
    
    @SerializedName("pageSize")
    private Integer pageSize;
    
    @SerializedName("category")
    private IngredientCategory category;
    
    @SerializedName("search")
    private String search;
    
    @SerializedName("expiryDateFrom")
    private Date expiryDateFrom;
    
    @SerializedName("expiryDateTo")
    private Date expiryDateTo;

    // Getters and Setters
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }
}
