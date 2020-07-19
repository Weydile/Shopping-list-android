package com.weydile.shoppinglist.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.weydile.shoppinglist.model.database.categories.Category;
import com.weydile.shoppinglist.model.database.categories.CategoryDao;
import com.weydile.shoppinglist.model.database.goods.Goods;
import com.weydile.shoppinglist.model.database.goods.GoodsDao;

@Database(entities = {Category.class, Goods.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract GoodsDao goodsDao();
}
