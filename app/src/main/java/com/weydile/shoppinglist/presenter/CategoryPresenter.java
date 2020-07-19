package com.weydile.shoppinglist.presenter;

import com.weydile.shoppinglist.model.CategoryModel;
import com.weydile.shoppinglist.model.database.categories.Category;

import java.util.ArrayList;

public class CategoryPresenter {

    private CategoryActivity activity;
    private ArrayList<Category> categories;
    private CategoryModel model;

    public CategoryPresenter(CategoryActivity activity, ArrayList<Category> categories) {
        this.activity = activity;
        this.categories = categories;
        this.model = new CategoryModel();
    }

    public void getAll(){
        model.getAll(categoriesList -> {
            categories.clear();
            categories.addAll(categoriesList);
            activity.updateView();
        });
    }

    public void add(Category category){
        model.add(this::getAll, category);
    }

    public void delete(Category category){
        model.delete(this::getAll, category);
    }
    public interface CategoryActivity{
        void updateView();
    }
}
