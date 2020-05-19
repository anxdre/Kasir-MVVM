package com.kasir.data.database.transaksiDetail;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kasir.data.model.TransaksiDetail;

import java.util.List;

@Dao
public interface TransaksiDetailDao {

    @Query("SELECT * FROM transaksi_detail_table")
    LiveData<List<TransaksiDetail>> getAllTranDetail();

    @Insert
    void insert(TransaksiDetail transaksiDetail);

    @Update
    void update(TransaksiDetail transaksiDetail);

    @Delete
    void delete(TransaksiDetail transaksiDetail);

    @Query("DELETE FROM transaksi_detail_table")
    void deleteAll();

    @Query("SELECT * FROM transaksi_detail_table WHERE idTransaksi = :idTrans")
    LiveData<List<TransaksiDetail>> getTranDet(int idTrans);
}
