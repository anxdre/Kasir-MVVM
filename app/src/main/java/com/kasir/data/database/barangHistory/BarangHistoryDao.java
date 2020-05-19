package com.kasir.data.database.barangHistory;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kasir.data.model.BarangHistory;

import java.util.List;

@Dao
public interface BarangHistoryDao {

    @Query("SELECT * FROM barang_history_table")
    LiveData<List<BarangHistory>> getAllBarangHistory();

    @Insert
    void insert(BarangHistory barangHistory);

    @Update
    void update(BarangHistory barangHistory);

    @Delete
    void delete(BarangHistory barangHistory);

    @Query("DELETE FROM barang_history_table")
    void deleteAll();

    @Query("SELECT * FROM barang_history_table WHERE ID = :idBar")
    LiveData<List<BarangHistory>> getBarangHis(int idBar);
}
