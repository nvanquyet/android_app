package com.example.android_exam.core.config;

/**
 * Centralized application configuration
 * All constants, endpoints, and config values should be defined here
 */
public class AppConfig {
    // API Configuration
    public static final String BASE_URL = "http://74.226.96.174/api/";
    public static final int API_TIMEOUT_SECONDS = 30;
    public static final String API_CONTENT_TYPE_JSON = "application/json";
    public static final String API_CONTENT_TYPE_IMAGE = "image/*";

    // API Endpoints
    public static class Endpoints {
        // Auth
        public static final String AUTH_LOGIN = "auth/login";
        public static final String AUTH_REGISTER = "auth/register";
        public static final String AUTH_VALIDATE_TOKEN = "auth/validateToken";
        public static final String AUTH_LOGOUT = "auth/logout";
        public static final String AUTH_ME = "auth/me";
        public static final String AUTH_UPDATE_PROFILE = "auth/me";
        public static final String AUTH_CHANGE_PASSWORD = "auth/change_password";

        // Food
        public static final String FOOD_BASE = "food";
        public static final String FOOD_BY_ID = FOOD_BASE + "/%s";
        public static final String FOOD_SUGGESTIONS = FOOD_BASE + "/suggestions";
        public static final String FOOD_RECIPES = FOOD_BASE + "/recipes";
        public static final String FOOD_CREATE = FOOD_BASE;
        public static final String FOOD_UPDATE = FOOD_BASE;
        public static final String FOOD_DELETE = FOOD_BASE;

        // Ingredient
        public static final String INGREDIENT_BASE = "ingredient";
        public static final String INGREDIENT_BY_ID = INGREDIENT_BASE + "/%s";
        public static final String INGREDIENT_LIST = INGREDIENT_BASE;
        public static final String INGREDIENT_CREATE = INGREDIENT_BASE;
        public static final String INGREDIENT_UPDATE = INGREDIENT_BASE + "/%s";
        public static final String INGREDIENT_DELETE = INGREDIENT_BASE + "/%s";

        // Nutrition
        public static final String NUTRITION_DAILY = "nutrition/daily";
        public static final String NUTRITION_WEEKLY = "nutrition/weekly";
        public static final String NUTRITION_OVERVIEW = "nutrition/overview";

        // AI
        public static final String AI_DETECT_FOOD = "ai/detect_food";
        public static final String AI_DETECT_INGREDIENT = "ai/detect_ingredient";
        
        // Health
        public static final String HEALTH = "health";
    }

    // Cache Configuration
    public static class Cache {
        public static final String PREF_NAME_FOOD = "FoodCache";
        public static final String KEY_SUGGESTIONS = "Suggestions";
        public static final long CACHE_EXPIRY_HOURS = 24;
    }

    // Date/Time Configuration
    public static class DateTime {
        public static final int EXPIRY_WARNING_DAYS = 3;
        public static final int DEFAULT_DAYS_RANGE = 5;
    }

    // UI Configuration
    public static class UI {
        public static final int LOADING_DELAY_MS = 300;
        public static final int ANIMATION_DURATION_MS = 200;
    }

    private AppConfig() {
        // Prevent instantiation
    }
}

