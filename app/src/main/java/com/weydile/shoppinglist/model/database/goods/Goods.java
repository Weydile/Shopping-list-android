package com.weydile.shoppinglist.model.database.goods;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.weydile.shoppinglist.model.database.categories.Category;

import static androidx.room.ForeignKey.CASCADE;


@Entity
        (foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = CASCADE))
public class Goods {

    public Goods() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_id", index = true)
    private int categoryId;

    private String categoryName;

    private String name;

    private String price;

    private String value;

    private boolean checked;

    @Ignore
    public Goods(String name, int categoryId, String categoryName) {
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        price = "0";
        value = "0";
        checked = false;
    }

    @Ignore
    public String getCost(){
        return String.valueOf(Float.parseFloat(price) * Float.parseFloat(value));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

