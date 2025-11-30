package com.example.android_exam.data.models.enums;

import androidx.annotation.NonNull;
import android.util.Log;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum IngredientCategory implements Serializable {
    // Giữ nguyên các giá trị cũ để tương thích với database
    @SerializedName("Dairy")
    DAIRY(0),
    @SerializedName("Meat")
    MEAT(1),
    @SerializedName("Vegetables")
    VEGETABLES(2),
    @SerializedName("Fruits")
    FRUITS(3),
    @SerializedName("Grains")
    GRAINS(4),
    @SerializedName("Seafood")
    SEAFOOD(5),
    @SerializedName("Beverages")
    BEVERAGES(6),
    @SerializedName("Condiments")
    CONDIMENTS(7),
    @SerializedName("Snacks")
    SNACKS(8),
    @SerializedName("Frozen")
    FROZEN(9),
    @SerializedName("Canned")
    CANNED(10),
    @SerializedName("Spices")
    SPICES(11),
    
    // Thêm các category mới
    @SerializedName("Poultry")
    POULTRY(20),
    @SerializedName("Eggs")
    EGGS(21),
    @SerializedName("Legumes")
    LEGUMES(22),
    @SerializedName("Nuts")
    NUTS(23),
    @SerializedName("Tofu")
    TOFU(24),
    
    // Vegetables subcategories
    @SerializedName("LeafyGreens")
    LEAFY_GREENS(30),
    @SerializedName("RootVegetables")
    ROOT_VEGETABLES(31),
    @SerializedName("Herbs")
    HERBS(32),
    
    // Fruits subcategories
    @SerializedName("Berries")
    BERRIES(40),
    @SerializedName("Citrus")
    CITRUS(41),
    
    // Grains & Starches subcategories
    @SerializedName("Rice")
    RICE(50),
    @SerializedName("Pasta")
    PASTA(51),
    @SerializedName("Bread")
    BREAD(52),
    @SerializedName("Noodles")
    NOODLES(53),
    
    // Cooking essentials
    @SerializedName("Oils")
    OILS(60),
    @SerializedName("Vinegar")
    VINEGAR(61),
    @SerializedName("Sauces")
    SAUCES(62),
    @SerializedName("Seasonings")
    SEASONINGS(63),
    
    // Baking
    @SerializedName("Baking")
    BAKING(70),
    @SerializedName("Flour")
    FLOUR(71),
    @SerializedName("Sugar")
    SUGAR(72),
    @SerializedName("Sweeteners")
    SWEETENERS(73),
    
    // Beverages subcategories
    @SerializedName("Alcoholic")
    ALCOHOLIC(80),
    
    // Processed foods
    @SerializedName("Processed")
    PROCESSED(90),
    
    // Other
    @SerializedName("Other")
    OTHER(99);

    private final int value;

    IngredientCategory(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public static IngredientCategory fromInt(int value) {
        for (IngredientCategory category : values()) {
            if (category.value == value) {
                return category;
            }
        }
        Log.w("IngredientCategory", "Unknown category value: " + value + ". Falling back to OTHER.");
        return OTHER;
    }

    // Method để convert từ string (API response)
    public static IngredientCategory fromString(String categoryString) {
        if (categoryString == null) return OTHER;

        return switch (categoryString) {
            // Giữ nguyên các giá trị cũ
            case "Dairy", "dairy" -> DAIRY;
            case "Meat", "meat" -> MEAT;
            case "Vegetables", "vegetables" -> VEGETABLES;
            case "Fruits", "fruits" -> FRUITS;
            case "Grains", "grains" -> GRAINS;
            case "Seafood", "seafood" -> SEAFOOD;
            case "Beverages", "beverages" -> BEVERAGES;
            case "Condiments", "condiments" -> CONDIMENTS;
            case "Snacks", "snacks" -> SNACKS;
            case "Frozen", "frozen" -> FROZEN;
            case "Canned", "canned" -> CANNED;
            case "Spices", "spices" -> SPICES;
            
            // Thêm các category mới
            case "Poultry", "poultry" -> POULTRY;
            case "Eggs", "eggs" -> EGGS;
            case "Legumes", "legumes" -> LEGUMES;
            case "Nuts", "nuts" -> NUTS;
            case "Tofu", "tofu" -> TOFU;
            
            // Vegetables subcategories
            case "LeafyGreens", "leafygreens" -> LEAFY_GREENS;
            case "RootVegetables", "rootvegetables" -> ROOT_VEGETABLES;
            case "Herbs", "herbs" -> HERBS;
            
            // Fruits subcategories
            case "Berries", "berries" -> BERRIES;
            case "Citrus", "citrus" -> CITRUS;
            
            // Grains & Starches subcategories
            case "Rice", "rice" -> RICE;
            case "Pasta", "pasta" -> PASTA;
            case "Bread", "bread" -> BREAD;
            case "Noodles", "noodles" -> NOODLES;
            
            // Cooking essentials
            case "Oils", "oils" -> OILS;
            case "Vinegar", "vinegar" -> VINEGAR;
            case "Sauces", "sauces" -> SAUCES;
            case "Seasonings", "seasonings" -> SEASONINGS;
            
            // Baking
            case "Baking", "baking" -> BAKING;
            case "Flour", "flour" -> FLOUR;
            case "Sugar", "sugar" -> SUGAR;
            case "Sweeteners", "sweeteners" -> SWEETENERS;
            
            // Beverages subcategories
            case "Alcoholic", "alcoholic" -> ALCOHOLIC;
            
            // Processed foods
            case "Processed", "processed" -> PROCESSED;
            
            // Other
            case "Other", "other" -> OTHER;
            
            default -> {
                Log.w("IngredientCategory", "Unknown category: " + categoryString);
                yield OTHER;
            }
        };
    }

    /**
     * Get API format name (e.g., "Vegetables", "Fruits")
     * Used for API requests
     */
    public String toApiName() {
        return switch (this) {
            // Giữ nguyên các giá trị cũ
            case DAIRY -> "Dairy";
            case MEAT -> "Meat";
            case VEGETABLES -> "Vegetables";
            case FRUITS -> "Fruits";
            case GRAINS -> "Grains";
            case SEAFOOD -> "Seafood";
            case BEVERAGES -> "Beverages";
            case CONDIMENTS -> "Condiments";
            case SNACKS -> "Snacks";
            case FROZEN -> "Frozen";
            case CANNED -> "Canned";
            case SPICES -> "Spices";
            
            // Thêm các category mới
            case POULTRY -> "Poultry";
            case EGGS -> "Eggs";
            case LEGUMES -> "Legumes";
            case NUTS -> "Nuts";
            case TOFU -> "Tofu";
            
            // Vegetables subcategories
            case LEAFY_GREENS -> "LeafyGreens";
            case ROOT_VEGETABLES -> "RootVegetables";
            case HERBS -> "Herbs";
            
            // Fruits subcategories
            case BERRIES -> "Berries";
            case CITRUS -> "Citrus";
            
            // Grains & Starches subcategories
            case RICE -> "Rice";
            case PASTA -> "Pasta";
            case BREAD -> "Bread";
            case NOODLES -> "Noodles";
            
            // Cooking essentials
            case OILS -> "Oils";
            case VINEGAR -> "Vinegar";
            case SAUCES -> "Sauces";
            case SEASONINGS -> "Seasonings";
            
            // Baking
            case BAKING -> "Baking";
            case FLOUR -> "Flour";
            case SUGAR -> "Sugar";
            case SWEETENERS -> "Sweeteners";
            
            // Beverages subcategories
            case ALCOHOLIC -> "Alcoholic";
            
            // Processed foods
            case PROCESSED -> "Processed";
            
            // Other
            case OTHER -> "Other";
        };
    }

    @NonNull
    @Override
    public String toString() {
        return switch (this) {
            // Giữ nguyên các giá trị cũ
            case DAIRY -> "Sữa";
            case MEAT -> "Thịt";
            case VEGETABLES -> "Rau củ";
            case FRUITS -> "Trái cây";
            case GRAINS -> "Ngũ cốc";
            case SEAFOOD -> "Hải sản";
            case BEVERAGES -> "Đồ uống";
            case CONDIMENTS -> "Nước chấm";
            case SNACKS -> "Đồ ăn nhẹ";
            case FROZEN -> "Đông lạnh";
            case CANNED -> "Đóng hộp";
            case SPICES -> "Gia vị";
            
            // Thêm các category mới
            case POULTRY -> "Thịt gia cầm";
            case EGGS -> "Trứng";
            case LEGUMES -> "Đậu, đỗ";
            case NUTS -> "Hạt, quả hạch";
            case TOFU -> "Đậu phụ";
            
            // Vegetables subcategories
            case LEAFY_GREENS -> "Rau lá xanh";
            case ROOT_VEGETABLES -> "Rau củ";
            case HERBS -> "Rau thơm";
            
            // Fruits subcategories
            case BERRIES -> "Quả mọng";
            case CITRUS -> "Cam quýt";
            
            // Grains & Starches subcategories
            case RICE -> "Gạo";
            case PASTA -> "Mì, pasta";
            case BREAD -> "Bánh mì";
            case NOODLES -> "Mì, phở, bún";
            
            // Cooking essentials
            case OILS -> "Dầu ăn";
            case VINEGAR -> "Giấm";
            case SAUCES -> "Nước sốt";
            case SEASONINGS -> "Gia vị nêm";
            
            // Baking
            case BAKING -> "Đồ làm bánh";
            case FLOUR -> "Bột";
            case SUGAR -> "Đường";
            case SWEETENERS -> "Chất tạo ngọt";
            
            // Beverages subcategories
            case ALCOHOLIC -> "Đồ uống có cồn";
            
            // Processed foods
            case PROCESSED -> "Thực phẩm chế biến sẵn";
            
            // Other
            case OTHER -> "Khác";
        };
    }
}
