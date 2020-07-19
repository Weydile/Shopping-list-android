package com.weydile.shoppinglist.model;

import android.os.AsyncTask;

import com.weydile.shoppinglist.App;
import com.weydile.shoppinglist.model.database.categories.Category;
import com.weydile.shoppinglist.model.database.categories.CategoryDao;

import java.util.List;

public class CategoryModel {

    private CategoryDao dao;

    public CategoryModel() {
        dao = App.getInstance().getDatabase().categoryDao();
    }

    public void getAll(GetCallback callback){
        new GetAsyncTask(callback, dao).execute();
    }

    public void add(SetCallback callback, Category category){
        new SetAsyncTask(callback, "add", dao).execute(category);
    }

    public void delete(SetCallback callback, Category category){
        new SetAsyncTask(callback, "delete", dao).execute(category);
    }

    public interface GetCallback{
        void callback(List<Category> categories);
    }

    public interface SetCallback{
        void callback();
    }

    static class GetAsyncTask extends AsyncTask<Void, Void, List<Category>>{

        private GetCallback callback;
        private CategoryDao dao;

        public GetAsyncTask(GetCallback callback, CategoryDao dao) {
            this.callback = callback;
            this.dao = dao;
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return dao.getAll();
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            callback.callback(categories);
        }
    }

    static class SetAsyncTask extends AsyncTask<Category, Void, Void>{

        private SetCallback callback;
        private String action;
        private CategoryDao dao;

        public SetAsyncTask(SetCallback callback, String action, CategoryDao dao) {
            this.callback = callback;
            this.action = action;
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            switch (action){
                case "add":
                    dao.insert(categories[0]);
                    break;
                case "delete":
                    dao.delete(categories[0]);
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
