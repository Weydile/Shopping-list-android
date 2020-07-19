package com.weydile.shoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.weydile.shoppinglist.R;
import com.weydile.shoppinglist.model.database.categories.Category;
import com.weydile.shoppinglist.presenter.Presenter;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private ArrayList<Category> categories;
    private Context context;
    private Presenter presenter;

    public CategoryAdapter(ArrayList<Category> categories, Context context, Presenter presenter) {
        this.categories = categories;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryVH(LayoutInflater.from(context)
                .inflate(R.layout.category_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        holder.categoryName.setText(categories.get(position).getName());
        holder.deleteCategoryButton
                .setOnClickListener(v -> presenter.deleteCategory(categories.get(position)));
        holder.cardView.setOnClickListener(v -> presenter
                .getGoods(categories.get(position).getId()
                        , categories.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryVH extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView categoryName;
        ImageButton deleteCategoryButton;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.category_card);
            categoryName = cardView.findViewById(R.id.category_name);
            deleteCategoryButton = cardView.findViewById(R.id.delete_button);
        }
    }
}
