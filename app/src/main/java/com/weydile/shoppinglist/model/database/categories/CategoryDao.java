package com.weydile.shoppinglist.model.database.categories;

import androidx.room.Dao;
import androidx.room.Query;


import com.weydile.shoppinglist.model.database.DatabaseDao;

import java.util.List;

@Dao
public interface CategoryDao extends DatabaseDao<Category> {

    @Query("SELECT * FROM category")
    List<Category> getAll();

}
