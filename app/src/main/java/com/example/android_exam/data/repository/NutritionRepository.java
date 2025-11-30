package com.example.android_exam.data.repository;

import android.util.Log;

import com.example.android_exam.core.datetime.DateTimeManager;
import com.example.android_exam.core.mapper.DtoMapper;
import com.example.android_exam.data.api.ApiManager;
import com.example.android_exam.data.api.DataCallback;
import com.example.android_exam.data.dto.nutrition.DailyNutritionSummaryDto;
import com.example.android_exam.data.dto.nutrition.UserNutritionRequestDto;
import com.example.android_exam.data.dto.nutrition.WeeklyNutritionSummaryDto;
import com.example.android_exam.data.dto.response.ApiResponse;
import com.example.android_exam.data.models.base.User;
import com.example.android_exam.utils.SessionManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NutritionRepository {
    private final DateTimeManager dateTimeManager = DateTimeManager.getInstance();
    private Map<String, DailyNutritionSummaryDto> dailyCache = new HashMap<>();
    private Map<String, WeeklyNutritionSummaryDto> weeklyCache = new HashMap<>();

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

    public void clearCache() {
        dailyCache.clear();
        weeklyCache.clear();
    }

    public void getDailyNutrition(Date date, Callback<DailyNutritionSummaryDto> callback) {
        Log.d("NutritionRepository", "getDailyNutrition called for date: " + date);
        // Sử dụng LocalDate để tránh timezone issues khi format key
        // Format key theo yyyy-MM-dd để consistent với API
        java.time.LocalDate localDate = dateTimeManager.convertToLocalDate(date);
        String dateKey = localDate != null ? localDate.format(dateTimeManager.DATE_FORMAT) : dateTimeManager.getTodayString();
        if (dailyCache.containsKey(dateKey)) {
            Log.d("NutritionRepository", "Returning cached data for date: " + dateKey);
            callback.onSuccess(dailyCache.get(dateKey));
            return;
        }

        SessionManager.getUser(new SessionManager.UserCallback() {
            @Override
            public void onUserLoaded(User user) {
                var userNutritionRequest = new UserNutritionRequestDto();
                // Sử dụng đúng ngày được chọn, không lùi ngày
                // Date sẽ được serialize thành ISO UTC string bởi DateTimeTypeAdapter
                userNutritionRequest.setCurrentDate(date);
                userNutritionRequest.setUserInformationDto(DtoMapper.toUserInformationDto(user));
                ApiManager.getInstance().getNutritionClient().getDailyNutritionSummary(userNutritionRequest, new DataCallback<ApiResponse<DailyNutritionSummaryDto>>() {
                    @Override
                    public void onSuccess(ApiResponse<DailyNutritionSummaryDto> result) {
                        //Add to cache
                        dailyCache.put(dateKey, result.getData());
                        callback.onSuccess(result.getData());
                        Log.d("NutritionRepository", "Fetched from server for date: " + result.getData().toString());
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError("Lỗi khi tải dữ liệu: " + error);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onError("Lỗi kết nối: " + throwable.getMessage());
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError("Lỗi khi tải dữ liệu: " + error);
            }
        });


    }

    public void getWeeklyNutrition(Date weekStart, Callback<WeeklyNutritionSummaryDto> callback) {
        // Sử dụng LocalDate để tránh timezone issues khi format key
        java.time.LocalDate localDate = dateTimeManager.convertToLocalDate(weekStart);
        String weekKey = (localDate != null ? localDate.format(dateTimeManager.DATE_FORMAT) : dateTimeManager.getTodayString()) + "-week";
        if (weeklyCache.containsKey(weekKey)) {
            callback.onSuccess(weeklyCache.get(weekKey));
            return;
        }

        //Get user information
        SessionManager.getUser(new SessionManager.UserCallback() {
            @Override
            public void onUserLoaded(User user) {
                var userNutritionRequest = new UserNutritionRequestDto();
                userNutritionRequest.setStartDate(weekStart);
                userNutritionRequest.setUserInformationDto(DtoMapper.toUserInformationDto(user));
                ApiManager.getInstance().getNutritionClient().getWeeklyNutritionSummary(userNutritionRequest, new DataCallback<ApiResponse<WeeklyNutritionSummaryDto>>() {
                    @Override
                    public void onSuccess(ApiResponse<WeeklyNutritionSummaryDto> result) {
                        //Add to cache
                        weeklyCache.put(weekKey, result.getData());
                        callback.onSuccess(result.getData());
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError("Lỗi khi tải dữ liệu: " + error);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onError("Lỗi kết nối: " + throwable.getMessage());
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError("Lỗi khi tải dữ liệu: " + error);
            }
        });


    }

}