package com.kasir.data.database.barang;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kasir.data.model.Barang;

import java.util.List;

@Dao
public interface BarangDao {

    @Query("SELECT * FROM barang_table")
    LiveData<List<Barang>> getAllBarangs();

    @Insert
    void insert(Barang barang);

    @Update
    void update(Barang barang);

    @Delete
    void delete(Barang barang);

    @Query("SELECT * FROM barang_table ORDER BY ID DESC LIMIT 1")
    LiveData<Barang> getId();
}
