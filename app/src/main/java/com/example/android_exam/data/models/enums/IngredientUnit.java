package com.example.android_exam.data.models.enums;

import androidx.annotation.NonNull;
import android.util.Log;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum IngredientUnit implements Serializable {
    // Weight Units (giữ nguyên giá trị cũ)
    @SerializedName("Kilogram")
    KILOGRAM(0),
    @SerializedName("Liter")
    LITER(1),
    @SerializedName("Piece")
    PIECE(2),
    @SerializedName("Box")
    BOX(3),
    @SerializedName("Gram")
    GRAM(4),
    @SerializedName("Milliliter")
    MILLILITER(5),
    @SerializedName("Can")
    CAN(6),
    @SerializedName("Cup")
    CUP(7),
    @SerializedName("Tablespoon")
    TABLESPOON(8),
    @SerializedName("Teaspoon")
    TEASPOON(9),
    @SerializedName("Package")
    PACKAGE(10),
    @SerializedName("Bottle")
    BOTTLE(11),
    
    // Thêm các đơn vị mới
    @SerializedName("Pound")
    POUND(20),
    @SerializedName("Ounce")
    OUNCE(21),
    @SerializedName("FluidOunce")
    FLUID_OUNCE(22),
    @SerializedName("Pint")
    PINT(23),
    @SerializedName("Quart")
    QUART(24),
    @SerializedName("Gallon")
    GALLON(25),
    
    // Countable Units
    @SerializedName("Slice")
    SLICE(30),
    @SerializedName("Clove")
    CLOVE(31),
    @SerializedName("Head")
    HEAD(32),
    @SerializedName("Bunch")
    BUNCH(33),
    @SerializedName("Stalk")
    STALK(34),
    @SerializedName("Wedge")
    WEDGE(35),
    @SerializedName("Sheet")
    SHEET(36),
    @SerializedName("Pod")
    POD(37),
    
    // Container Units
    @SerializedName("Bag")
    BAG(40),
    @SerializedName("Jar")
    JAR(41),
    @SerializedName("Tube")
    TUBE(42),
    @SerializedName("Carton")
    CARTON(43),
    
    // Small Quantity Units
    @SerializedName("Pinch")
    PINCH(50),
    @SerializedName("Dash")
    DASH(51),
    @SerializedName("Drop")
    DROP(52),
    
    // Other
    @SerializedName("Serving")
    SERVING(60),
    @SerializedName("Portion")
    PORTION(61),
    @SerializedName("Other")
    OTHER(99);

    private final int value;

    IngredientUnit(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public static IngredientUnit fromInt(int value) {
        for (IngredientUnit unit : values()) {
            if (unit.value == value) {
                return unit;
            }
        }
        Log.w("IngredientUnit", "Unknown unit value: " + value + ". Falling back to OTHER.");
        return OTHER;
    }

    // Method để convert từ string (API response)
    public static IngredientUnit fromString(String unitString) {
        if (unitString == null) return OTHER;

        return switch (unitString) {
            // Weight Units
            case "Kilogram", "kilogram", "kg" -> KILOGRAM;
            case "Gram", "gram", "g" -> GRAM;
            case "Pound", "pound", "lb" -> POUND;
            case "Ounce", "ounce", "oz" -> OUNCE;
            
            // Volume Units
            case "Liter", "liter", "l", "L" -> LITER;
            case "Milliliter", "milliliter", "ml", "mL" -> MILLILITER;
            case "Cup", "cup" -> CUP;
            case "Tablespoon", "tablespoon" -> TABLESPOON;
            case "Teaspoon", "teaspoon" -> TEASPOON;
            case "FluidOunce", "fluidounce", "fl oz" -> FLUID_OUNCE;
            case "Pint", "pint" -> PINT;
            case "Quart", "quart" -> QUART;
            case "Gallon", "gallon" -> GALLON;
            
            // Countable Units
            case "Piece", "piece" -> PIECE;
            case "Slice", "slice" -> SLICE;
            case "Clove", "clove" -> CLOVE;
            case "Head", "head" -> HEAD;
            case "Bunch", "bunch" -> BUNCH;
            case "Stalk", "stalk" -> STALK;
            case "Wedge", "wedge" -> WEDGE;
            case "Sheet", "sheet" -> SHEET;
            case "Pod", "pod" -> POD;
            
            // Container Units
            case "Box", "box" -> BOX;
            case "Can", "can" -> CAN;
            case "Bottle", "bottle" -> BOTTLE;
            case "Package", "package" -> PACKAGE;
            case "Bag", "bag" -> BAG;
            case "Jar", "jar" -> JAR;
            case "Tube", "tube" -> TUBE;
            case "Carton", "carton" -> CARTON;
            
            // Small Quantity Units
            case "Pinch", "pinch" -> PINCH;
            case "Dash", "dash" -> DASH;
            case "Drop", "drop" -> DROP;
            
            // Other Units
            case "Serving", "serving" -> SERVING;
            case "Portion", "portion" -> PORTION;
            case "Other", "other" -> OTHER;
            
            default -> {
                Log.w("IngredientUnit", "Unknown unit: " + unitString);
                yield OTHER;
            }
        };
    }

    /**
     * Get API format name (e.g., "Gram", "Kilogram")
     * Used for API requests
     */
    public String toApiName() {
        return switch (this) {
            // Weight Units
            case KILOGRAM -> "Kilogram";
            case GRAM -> "Gram";
            case POUND -> "Pound";
            case OUNCE -> "Ounce";
            
            // Volume Units
            case LITER -> "Liter";
            case MILLILITER -> "Milliliter";
            case CUP -> "Cup";
            case TABLESPOON -> "Tablespoon";
            case TEASPOON -> "Teaspoon";
            case FLUID_OUNCE -> "FluidOunce";
            case PINT -> "Pint";
            case QUART -> "Quart";
            case GALLON -> "Gallon";
            
            // Countable Units
            case PIECE -> "Piece";
            case SLICE -> "Slice";
            case CLOVE -> "Clove";
            case HEAD -> "Head";
            case BUNCH -> "Bunch";
            case STALK -> "Stalk";
            case WEDGE -> "Wedge";
            case SHEET -> "Sheet";
            case POD -> "Pod";
            
            // Container Units
            case BOX -> "Box";
            case CAN -> "Can";
            case BOTTLE -> "Bottle";
            case PACKAGE -> "Package";
            case BAG -> "Bag";
            case JAR -> "Jar";
            case TUBE -> "Tube";
            case CARTON -> "Carton";
            
            // Small Quantity Units
            case PINCH -> "Pinch";
            case DASH -> "Dash";
            case DROP -> "Drop";
            
            // Other Units
            case SERVING -> "Serving";
            case PORTION -> "Portion";
            case OTHER -> "Other";
        };
    }

    @NonNull
    @Override
    public String toString() {
        return switch (this) {
            // Weight Units
            case KILOGRAM -> "kg";
            case GRAM -> "g";
            case POUND -> "lb";
            case OUNCE -> "oz";
            
            // Volume Units
            case LITER -> "l";
            case MILLILITER -> "ml";
            case CUP -> "cốc";
            case TABLESPOON -> "muỗng canh";
            case TEASPOON -> "muỗng cà phê";
            case FLUID_OUNCE -> "fl oz";
            case PINT -> "pint";
            case QUART -> "quart";
            case GALLON -> "gallon";
            
            // Countable Units
            case PIECE -> "cái";
            case SLICE -> "lát";
            case CLOVE -> "tép";
            case HEAD -> "củ";
            case BUNCH -> "bó";
            case STALK -> "cọng";
            case WEDGE -> "miếng";
            case SHEET -> "lá";
            case POD -> "quả";
            
            // Container Units
            case BOX -> "hộp";
            case CAN -> "lon";
            case BOTTLE -> "chai";
            case PACKAGE -> "gói";
            case BAG -> "túi";
            case JAR -> "lọ";
            case TUBE -> "tuýp";
            case CARTON -> "thùng";
            
            // Small Quantity Units
            case PINCH -> "nhúm";
            case DASH -> "chút";
            case DROP -> "giọt";
            
            // Other Units
            case SERVING -> "phần";
            case PORTION -> "suất";
            case OTHER -> "khác";
        };
    }
}
