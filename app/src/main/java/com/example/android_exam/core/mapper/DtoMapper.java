package com.example.android_exam.core.mapper;

import com.example.android_exam.data.dto.user.UserInformationDto;
import com.example.android_exam.data.dto.user.UserProfileDto;
import com.example.android_exam.data.models.base.User;

/**
 * Centralized DTO to Model mapper
 * All DTO conversions should go through this class
 */
public class DtoMapper {

    /**
     * Convert User to UserInformationDto
     */
    public static UserInformationDto toUserInformationDto(User user) {
        if (user == null) {
            return null;
        }

        UserInformationDto dto = new UserInformationDto();
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setGender(user.getGender());
        dto.setHeight(user.getHeight());
        dto.setWeight(user.getWeight());
        dto.setTargetWeight(user.getTargetWeight());
        dto.setPrimaryNutritionGoal(user.getPrimaryNutritionGoal());
        dto.setActivityLevel(user.getActivityLevel());
        return dto;
    }

    /**
     * Convert User to UserProfileDto
     */
    public static UserProfileDto toUserProfileDto(User user) {
        if (user == null) {
            return null;
        }
        return UserProfileDto.fromUser(user);
    }

    /**
     * Convert UserProfileDto to User
     */
    public static User toUser(UserProfileDto dto) {
        if (dto == null) {
            return null;
        }
        return dto.toUser();
    }

    // Add more mapper methods as needed for other DTOs
}

