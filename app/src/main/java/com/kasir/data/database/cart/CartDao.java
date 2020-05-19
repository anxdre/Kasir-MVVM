package com.kasir.data.database.cart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kasir.data.model.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart_table")
    LiveData<List<Cart>> getAllCarts();

    @Insert
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * FROM cart_table")
    LiveData<Cart> getCarts();
}
