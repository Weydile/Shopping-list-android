package com.weydile.shoppinglist.model;

import android.os.AsyncTask;

import com.weydile.shoppinglist.App;
import com.weydile.shoppinglist.model.database.goods.Goods;
import com.weydile.shoppinglist.model.database.goods.GoodsDao;

import java.util.List;

public class GoodsModel {

    private GoodsDao dao;

    public GoodsModel() {
        dao = App.getInstance().getDatabase().goodsDao();
    }

    public void getGoods(GetCallback callback, int categoryId){
        new GetAsyncTask(callback, dao).execute(categoryId);
    }

    public void add(SetCallback callback, Goods goods){
        new SetAsyncTask(callback, "add", dao).execute(goods);
    }

    public void update(SetCallback callback, Goods goods){
        new SetAsyncTask(callback, "update", dao).execute(goods);
    }

    public void delete(SetCallback callback, Goods goods){
        new SetAsyncTask(callback, "delete", dao).execute(goods);
    }

    public interface GetCallback{
        void callback(List<Goods> goodsList);
    }

    public interface SetCallback{
        void callback();
    }

    static class GetAsyncTask extends AsyncTask<Integer, Void, List<Goods>>{

        private GetCallback callback;
        private GoodsDao dao;

        public GetAsyncTask(GetCallback callback, GoodsDao dao) {
            this.callback = callback;
            this.dao = dao;
        }


        @Override
        protected List<Goods> doInBackground(Integer... integers) {
            return dao.getAllFromCategory(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Goods> goods) {
            callback.callback(goods);
        }
    }

    static class SetAsyncTask extends AsyncTask<Goods, Void, Void> {

        private SetCallback callback;
        private String action;
        private GoodsDao dao;

        public SetAsyncTask(SetCallback callback, String action, GoodsDao dao) {
            this.callback = callback;
            this.action = action;
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Goods... goods) {
            switch (action){
                case "add":
                    dao.insert(goods[0]);
                    break;
                case "update":
                    dao.update(goods[0]);
                    break;
                case "delete":
                    dao.delete(goods[0]);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.callback();
        }
    }
}
