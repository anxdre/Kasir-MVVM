package com.kasir.ui.transaksiDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kasir.data.model.TransaksiDetail;

import java.util.List;

public class TransaksiDetailViewModel extends AndroidViewModel {
    private TransaksiDetailRepository repository;
    private LiveData<List<TransaksiDetail>> getAllTransaksiDetail;

    public TransaksiDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new TransaksiDetailRepository(application);
        getAllTransaksiDetail = repository.getGetAllTransaksiDetail();
    }

    public LiveData<List<TransaksiDetail>> getTranDet(int idTran){
        return repository.getTranDet(idTran);
    }

    public LiveData<List<TransaksiDetail>> getGetAllTransaksiDetail(){
        return getAllTransaksiDetail;
    }

    public void insert(TransaksiDetail transaksiDetail){
        repository.insert(transaksiDetail);
    }

    public void update(TransaksiDetail transaksiDetail){
        repository.update(transaksiDetail);
    }

    public void delete(TransaksiDetail transaksiDetail){
        repository.delete(transaksiDetail);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
