package com.weydile.shoppinglist.model.database.goods;

import androidx.room.Dao;
import androidx.room.Query;

import com.weydile.shoppinglist.model.database.DatabaseDao;

import java.util.List;

@Dao
public interface GoodsDao extends DatabaseDao<Goods> {

    @Query("SELECT * FROM goods WHERE category_id = :categoryId")
    List<Goods> getAllFromCategory(int categoryId);

}
