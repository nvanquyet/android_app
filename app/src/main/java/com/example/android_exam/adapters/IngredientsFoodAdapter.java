package com.example.android_exam.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_exam.R;
import com.example.android_exam.data.dto.food.FoodIngredientDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class IngredientsFoodAdapter extends RecyclerView.Adapter<IngredientsFoodAdapter.IngredientViewHolder> {

    private List<FoodIngredientDto> ingredients = new ArrayList<>();
    private OnIngredientInteractionListener listener;

    public interface OnIngredientInteractionListener {
        void onQuantityChanged(int ingredientId, BigDecimal newQuantity);
    }

    public IngredientsFoodAdapter(OnIngredientInteractionListener listener) {
        this.listener = listener;
    }

    public void setIngredients(List<FoodIngredientDto> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient_in_food, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        FoodIngredientDto ingredient = ingredients.get(position);
        holder.bind(ingredient, listener);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIngredientName;
        private TextView tvIngredientUnit;
        private TextView tvQuantity;
        private ImageButton btnDecrease;
        private ImageButton btnIncrease;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvIngredientUnit = itemView.findViewById(R.id.tv_unit);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
        }

        @SuppressLint("SetTextI18n")
        public void bind(FoodIngredientDto ingredient, OnIngredientInteractionListener listener) {
            // Set ingredient name
            tvIngredientName.setText(ingredient.getIngredientName() != null
                    ? ingredient.getIngredientName() : "Unknown Ingredient");

            // Set quantity (check null)
            BigDecimal quantity = ingredient.getQuantity() != null ? ingredient.getQuantity() : BigDecimal.ZERO;
            tvQuantity.setText(formatQuantity(quantity));

            // Set unit
            if (ingredient.getUnit() != null) {
                tvIngredientUnit.setText(ingredient.getUnit().toString());
            } else {
                tvIngredientUnit.setText("Unknown Unit");
            }

            // Chỉ hiển thị nút tăng/giảm cho nguyên liệu có trong kho (ingredientId > 0)
            boolean isInInventory = ingredient.getIngredientId() != null && ingredient.getIngredientId() > 0;
            
            if (isInInventory) {
                btnDecrease.setVisibility(View.VISIBLE);
                btnIncrease.setVisibility(View.VISIBLE);
                
                // Setup decrease button
                btnDecrease.setOnClickListener(v -> {
                    BigDecimal currentQty = ingredient.getQuantity() != null ? ingredient.getQuantity() : BigDecimal.ZERO;
                    BigDecimal newQty = currentQty.subtract(getStepSize(ingredient.getUnit()));
                    if (newQty.compareTo(BigDecimal.ZERO) < 0) {
                        newQty = BigDecimal.ZERO;
                    }
                    ingredient.setQuantity(newQty);
                    tvQuantity.setText(formatQuantity(newQty));
                    if (listener != null) {
                        listener.onQuantityChanged(ingredient.getIngredientId(), newQty);
                    }
                });
                
                // Setup increase button
                btnIncrease.setOnClickListener(v -> {
                    BigDecimal currentQty = ingredient.getQuantity() != null ? ingredient.getQuantity() : BigDecimal.ZERO;
                    BigDecimal newQty = currentQty.add(getStepSize(ingredient.getUnit()));
                    ingredient.setQuantity(newQty);
                    tvQuantity.setText(formatQuantity(newQty));
                    if (listener != null) {
                        listener.onQuantityChanged(ingredient.getIngredientId(), newQty);
                    }
                });
            } else {
                btnDecrease.setVisibility(View.GONE);
                btnIncrease.setVisibility(View.GONE);
            }
        }

        /**
         * Lấy bước nhảy phù hợp với đơn vị
         */
        private BigDecimal getStepSize(com.example.android_exam.data.models.enums.IngredientUnit unit) {
            if (unit == null) {
                return new BigDecimal("1");
            }
            
            // Với đơn vị khối lượng nhỏ (Gram, Teaspoon, etc.) -> bước nhảy 0.1
            // Với đơn vị lớn (Kilogram, Liter, etc.) -> bước nhảy 0.5
            // Với đơn vị đếm (Piece, Slice, etc.) -> bước nhảy 1
            switch (unit) {
                // Weight Units - nhỏ
                case GRAM:
                case OUNCE:
                // Volume Units - nhỏ
                case MILLILITER:
                case TEASPOON:
                case TABLESPOON:
                case FLUID_OUNCE:
                // Small Quantity Units
                case PINCH:
                case DASH:
                case DROP:
                    return new BigDecimal("0.1");
                
                // Weight Units - lớn
                case KILOGRAM:
                case POUND:
                // Volume Units - lớn
                case LITER:
                case CUP:
                case PINT:
                case QUART:
                case GALLON:
                    return new BigDecimal("0.5");
                
                // Countable Units
                case PIECE:
                case SLICE:
                case CLOVE:
                case HEAD:
                case BUNCH:
                case STALK:
                case WEDGE:
                case SHEET:
                case POD:
                // Container Units
                case BOX:
                case CAN:
                case BOTTLE:
                case PACKAGE:
                case BAG:
                case JAR:
                case TUBE:
                case CARTON:
                // Other Units
                case SERVING:
                case PORTION:
                case OTHER:
                    return new BigDecimal("1");
                
                default:
                    return new BigDecimal("0.1");
            }
        }

        /**
         * Format quantity để hiển thị đẹp (loại bỏ số 0 thừa)
         */
        private String formatQuantity(BigDecimal quantity) {
            if (quantity == null) {
                return "0";
            }
            // Nếu là số nguyên thì hiển thị không có phần thập phân
            if (quantity.scale() == 0 || quantity.stripTrailingZeros().scale() <= 0) {
                return quantity.toBigInteger().toString();
            }
            // Nếu có phần thập phân thì hiển thị tối đa 2 chữ số
            return quantity.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        }
    }
}