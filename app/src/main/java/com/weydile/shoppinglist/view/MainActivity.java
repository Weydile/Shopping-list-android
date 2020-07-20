package com.weydile.shoppinglist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.weydile.shoppinglist.R;
import com.weydile.shoppinglist.adapters.CategoryAdapter;
import com.weydile.shoppinglist.adapters.GoodsAdapter;
import com.weydile.shoppinglist.model.database.categories.Category;
import com.weydile.shoppinglist.model.database.goods.Goods;
import com.weydile.shoppinglist.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Presenter.Activity, GoodsAdapter.Activity {

    private ArrayList<Category> categories;
    private ArrayList<Goods> goods;
    private Presenter presenter;
    private CategoryAdapter categoryAdapter;
    private GoodsAdapter goodsAdapter;
    private boolean inputIsHide;
    private boolean isGoods;
    private int categoryId;
    private String categoryName;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        init();
        setListeners();
        presenter.getCategories();
    }

    private void init() {
        inputIsHide = true;

        categories = new ArrayList<>();
        goods = new ArrayList<>();
        presenter = new Presenter();
        presenter.attachActivity(this);

        categoryAdapter = new CategoryAdapter(categories, this, presenter);
        goodsAdapter = new GoodsAdapter(goods, this, presenter);

    }

    private void setListeners() {
        findViewById(R.id.floatingActionButton).setOnClickListener(v -> showInputField());
        ((TextInputLayout) findViewById(R.id.input_name))
                .setEndIconOnClickListener(v -> {
                    String name = ((TextInputEditText) findViewById(R.id.input_name_text))
                            .getText().toString();
                    if (name.equals("")){
                        Toast.makeText(this, R.string.type_something, Toast.LENGTH_SHORT).show();
                    }else {
                        if (isGoods) {
                            Goods goods = new Goods(name, categoryId, categoryName);
                            presenter.addGoods(goods);
                        } else {
                            Category category = new Category(name);
                            presenter.addCategory(category);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_all_selected_goods:
                DialogFragment dialogFragment = new DeleteAllDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"DeleteAllSelectedGoods");
                return true;
            case R.id.select_all_goods:
                selectAllGoods();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showDeleteGoodsButton(){
        optionsMenu.findItem(R.id.delete_all_selected_goods).setVisible(true);
    }

    @Override
    public void hideDeleteGoodsButton(){
        optionsMenu.findItem(R.id.delete_all_selected_goods).setVisible(false);
    }

    public void deleteAllSelectedGoods(){
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).isChecked()){
                presenter.deleteGoods(goods.get(i));
            }
        }
        hideDeleteGoodsButton();
    }

    public void selectAllGoods(){
        for (int i = 0; i < goods.size(); i++) {
            goods.get(i).setChecked(true);
        }
        updateView();
    }

    private void hideInputField() {
        inputIsHide = true;

        findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
        findViewById(R.id.input_name).setVisibility(View.GONE);

        ((TextInputEditText) findViewById(R.id.input_name_text)).setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(findViewById(R.id.input_name_text).getWindowToken(), 0);

    }

    private void showInputField() {
        inputIsHide = false;

        findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        findViewById(R.id.input_name).setVisibility(View.VISIBLE);

        findViewById(R.id.input_name_text).requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(findViewById(R.id.input_name_text), InputMethodManager.SHOW_IMPLICIT);
    }

    public void updateView() {
        if (isGoods) {
            goodsAdapter.notifyDataSetChanged();
        } else {
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateCost() {
        float totalCost = 0;
        for (int i = 0; i < goods.size(); i++) {
            totalCost += Float.parseFloat(goods.get(i).getCost());
        }
        ((TextView) findViewById(R.id.total_cost)).setText(String.valueOf(totalCost));
    }

    @Override
    public void onBackPressed() {
        if (!inputIsHide) {
            hideInputField();
        } else if (isGoods) {
            hideDeleteGoodsButton();
            optionsMenu.findItem(R.id.select_all_goods).setVisible(false);
            showCategories();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public List<Goods> getGoods() {
        return goods;
    }

    @Override
    public void showCategories() {
        ((RecyclerView) findViewById(R.id.main_list))
                .setAdapter(categoryAdapter);
        ((TextInputLayout) findViewById(R.id.input_name))
                .setHint(getString(R.string.type_a_new_category_name));
        hideInputField();

        getSupportActionBar().setTitle(R.string.categories);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        findViewById(R.id.total_cost_layout).setVisibility(View.GONE);

        isGoods = false;
        updateView();
    }

    @Override
    public void showGoods(int categoryId, String categoryName) {
        findViewById(R.id.total_cost_layout).setVisibility(View.VISIBLE);
        ((RecyclerView) findViewById(R.id.main_list))
                .setAdapter(goodsAdapter);
        ((TextInputLayout) findViewById(R.id.input_name))
                .setHint(getString(R.string.what_you_want_to_buy));

        hideInputField();

        isGoods = true;
        this.categoryId = categoryId;
        this.categoryName = categoryName;

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (goods.size() > 0){
            optionsMenu.findItem(R.id.select_all_goods).setVisible(true);
        }else{
            optionsMenu.findItem(R.id.select_all_goods).setVisible(false);
        }
        updateCost();
        updateView();
    }
}