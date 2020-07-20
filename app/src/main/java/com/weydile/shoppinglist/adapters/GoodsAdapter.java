package com.weydile.shoppinglist.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.weydile.shoppinglist.R;
import com.weydile.shoppinglist.model.database.goods.Goods;
import com.weydile.shoppinglist.presenter.Presenter;
import com.weydile.shoppinglist.view.MainActivity;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsVH> {

    private ArrayList<Goods> goods;
    private Context context;
    private Presenter presenter;
    private Activity activity;


    public GoodsAdapter(ArrayList<Goods> goods, Context context, Presenter presenter) {
        this.goods = goods;
        this.context = context;
        this.presenter = presenter;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public GoodsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsAdapter.GoodsVH(LayoutInflater.from(context)
                .inflate(R.layout.goods_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsVH holder, int position) {
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            goods.get(position).setChecked(isChecked);
            presenter.updateGoods(goods.get(position));
            activity.updateCost();
            if (holder.checkBox.isChecked()) {
                holder.uncheckedGoods.setVisibility(View.GONE);
                holder.checkedGoods.setVisibility(View.VISIBLE);
                holder.goodsCost.setText(goods.get(position).getCost());
            } else {
                holder.checkedGoods.setVisibility(View.GONE);
                holder.uncheckedGoods.setVisibility(View.VISIBLE);
            }
            int checkedGoods = 0;
            for (int i = 0; i < goods.size(); i++) {
                if (goods.get(i).isChecked()){
                    checkedGoods++;
                }
                if (checkedGoods!=0){
                    activity.showDeleteGoodsButton();
                }else{
                    activity.hideDeleteGoodsButton();
                }
            }
        });

        if (goods.get(position).getPrice().equals("0")) {
            holder.goodsPrice.setText("");
        } else {
            holder.goodsPrice.setText(goods.get(position).getPrice());
        }
        if (goods.get(position).getValue().equals("0")) {
            holder.goodsValue.setText("");
        } else {
            holder.goodsValue.setText(goods.get(position).getValue());
        }

        holder.goodsValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    goods.get(position).setValue("0");
                } else {
                    goods.get(position).setValue(s.toString());
                }
                activity.updateCost();
                presenter.updateGoods(goods.get(position));
            }
        });
        holder.goodsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    goods.get(position).setPrice("0");
                } else {
                    goods.get(position).setPrice(s.toString());
                }
                activity.updateCost();
                presenter.updateGoods(goods.get(position));
            }
        });
        holder.goodsName.setText(goods.get(position).getName());
        holder.checkBox.setChecked(goods.get(position).isChecked());

    }

    public interface Activity{
        void updateCost();
        void showDeleteGoodsButton();
        void hideDeleteGoodsButton();
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    static class GoodsVH extends RecyclerView.ViewHolder {

        TextView goodsName;
        CheckBox checkBox;
        EditText goodsPrice;
        EditText goodsValue;
        TextView goodsCost;
        ConstraintLayout uncheckedGoods;
        ConstraintLayout checkedGoods;

        public GoodsVH(@NonNull View itemView) {
            super(itemView);
            goodsName = itemView.findViewById(R.id.goods_name);
            checkBox = itemView.findViewById(R.id.goods_check_box);
            goodsPrice = itemView.findViewById(R.id.price_edit_text);
            goodsValue = itemView.findViewById(R.id.value_edit_text);
            goodsCost = itemView.findViewById(R.id.cost_text_view);
            uncheckedGoods = itemView.findViewById(R.id.unchecked_goods);
            checkedGoods = itemView.findViewById(R.id.checked_goods);
        }
    }
}
