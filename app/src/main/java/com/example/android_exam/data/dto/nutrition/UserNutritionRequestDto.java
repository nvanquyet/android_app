package com.example.android_exam.data.dto.nutrition;

import com.example.android_exam.core.datetime.DateTimeTypeAdapter;
import com.example.android_exam.data.dto.user.UserInformationDto;
import com.google.gson.annotations.JsonAdapter;
import java.util.Date;

public class UserNutritionRequestDto {
    @JsonAdapter(DateTimeTypeAdapter.class)
    private Date currentDate;
    @JsonAdapter(DateTimeTypeAdapter.class)
    private Date startDate;
    @JsonAdapter(DateTimeTypeAdapter.class)
    private Date endDate;
    private UserInformationDto userInformationDto;

    public UserNutritionRequestDto() {
        this.currentDate = new Date();
        this.userInformationDto = new UserInformationDto();
    }

    // Getters and setters
    public Date getCurrentDate() { return currentDate; }
    public void setCurrentDate(Date currentDate) { this.currentDate = currentDate; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        //End date is set to 7 days after start date by default
        // Sử dụng Calendar để đảm bảo tính toán đúng với timezone
        if (startDate != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(java.util.Calendar.DAY_OF_MONTH, 7);
            this.endDate = cal.getTime();
        }
    }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public UserInformationDto getUserInformationDto() { return userInformationDto; }
    public void setUserInformationDto(UserInformationDto userInformationDto) { this.userInformationDto = userInformationDto; }
}
