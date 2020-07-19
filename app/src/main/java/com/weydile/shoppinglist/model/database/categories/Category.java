package com.weydile.shoppinglist.model.database.categories;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @Ignore
    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
