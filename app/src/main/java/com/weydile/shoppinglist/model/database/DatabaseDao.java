package com.weydile.shoppinglist.model.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface DatabaseDao<T> {

    @Insert
    void insert(T object);

    @Update
    void update(T object);

    @Delete
    void delete(T object);
}
