package com.kasir.ui.transaksi;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kasir.data.database.cart.CartDao;
import com.kasir.data.database.cart.CartDatabase;
import com.kasir.data.model.Cart;

import java.util.List;

public class TransaksiRepository {
    private CartDao cartDao;
    private LiveData<List<Cart>> allCarts;
    private LiveData<Cart> allCart;

    public TransaksiRepository(Application application){
        CartDatabase database = CartDatabase.getInstance(application);
        cartDao = database.cartDao();
        allCarts = cartDao.getAllCarts();
        allCart = cartDao.getCarts();
    }

    public LiveData<Cart> allCart(){
        return allCart;
    }

    public void insert(Cart cart){
        new InsertBarangAsyncTask(cartDao).execute(cart);
    }

    public void update(Cart cart){
        new UpdateBarangAsyncTask(cartDao).execute(cart);
    }

    public void delete(Cart cart){
        new DeleteBarangAsyncTask(cartDao).execute(cart);
    }

    public void deleteAll(){
        new DeleteAllBarangAsyncTask(cartDao).execute();
    }

    public LiveData<List<Cart>> getAllCarts(){
        return allCarts;
    }

    private static class InsertBarangAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private InsertBarangAsyncTask(CartDao cartDao){
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }

    private static class UpdateBarangAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private UpdateBarangAsyncTask(CartDao cartDao){
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }

    private static class DeleteBarangAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private DeleteBarangAsyncTask(CartDao cartDao){
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.delete(carts[0]);
            return null;
        }
    }

    private static class DeleteAllBarangAsyncTask extends AsyncTask<Void, Void, Void> {
        private CartDao cartDao;

        private DeleteAllBarangAsyncTask(CartDao cartDao){
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... carts) {
            cartDao.deleteAll();
            return null;
        }
    }
}

