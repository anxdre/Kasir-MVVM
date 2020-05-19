package com.kasir.data.database.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kasir.data.model.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM history_table")
    LiveData<List<History>> getAllHistory();

    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();

    @Query("SELECT * FROM history_table ORDER BY ID DESC LIMIT 1")
    LiveData<History> getId();

}
