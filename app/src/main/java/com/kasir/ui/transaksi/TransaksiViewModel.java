package com.kasir.ui.transaksi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kasir.data.model.Cart;

import java.util.List;

public class TransaksiViewModel extends AndroidViewModel {
    private TransaksiRepository repository;
    private LiveData<List<Cart>> allCarts;
    private LiveData<Cart> allCart;

    public TransaksiViewModel(@NonNull Application application) {
        super(application);
        repository = new TransaksiRepository(application);
        allCarts = repository.getAllCarts();
        allCart = repository.allCart();
    }

    public LiveData<Cart> allCart(){
        return allCart;
    }

    public void insert(Cart cart){
        repository.insert(cart);
    }

    public void update(Cart cart){
        repository.update(cart);
    }

    public void delete(Cart cart){
        repository.delete(cart);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }
}
