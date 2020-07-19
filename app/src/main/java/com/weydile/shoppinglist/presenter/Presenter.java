package com.weydile.shoppinglist.presenter;

import android.os.SystemClock;
import android.util.Log;

import com.weydile.shoppinglist.model.CategoryModel;
import com.weydile.shoppinglist.model.GoodsModel;
import com.weydile.shoppinglist.model.database.categories.Category;
import com.weydile.shoppinglist.model.database.goods.Goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Presenter implements Serializable {

    private Activity activity;
    private ArrayList<Category> categories;
    private CategoryModel categoryModel;
    private ArrayList<Goods> goods;
    private GoodsModel goodsModel;

    private void init() {
        this.categories = (ArrayList<Category>) activity.getCategories();
        this.categoryModel = new CategoryModel();
        this.goods = (ArrayList<Goods>) activity.getGoods();
        this.goodsModel = new GoodsModel();
    }

    public void attachActivity(Activity activity) {
        this.activity = activity;
        init();
    }

    public void detachActivity() {
        activity = null;
    }

    public void getCategories() {
        categoryModel.getAll(categoriesList -> {
            categories.clear();
            categories.addAll(categoriesList);
            activity.showCategories();
        });
    }

    public void addCategory(Category category) {
        categoryModel.add(this::getCategories, category);
    }

    public void deleteCategory(Category category){
        categoryModel.delete(this::getCategories, category);
    }

    public void getGoods(int categoryId, String categoryName) {
        goodsModel.getGoods(goodsList -> {
            goods.clear();
            goods.addAll(goodsList);
            activity.showGoods(categoryId, categoryName);
        }, categoryId);
    }

    public void addGoods(Goods goods) {
        goodsModel.add(() -> getGoods(goods.getCategoryId(), goods.getCategoryName()), goods);
    }

    public void updateGoods(Goods goods){
        goodsModel.update(() -> {}, goods);
    }

    public void deleteGoods(Goods goods){
        goodsModel.delete(() -> getGoods(goods.getCategoryId(), goods.getCategoryName()), goods);
    }


    public interface Activity {

        List<Category> getCategories();

        List<Goods> getGoods();

        void showCategories();

        void showGoods(int categoryId, String categoryName);
    }

}
