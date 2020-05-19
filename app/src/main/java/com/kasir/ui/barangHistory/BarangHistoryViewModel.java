package com.kasir.ui.barangHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kasir.data.model.Barang;
import com.kasir.data.model.BarangHistory;

import java.util.List;

public class BarangHistoryViewModel extends AndroidViewModel {
    private BarangHistoryRepository repository;
    private LiveData<List<BarangHistory>> allBarangHistories;

    public BarangHistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new BarangHistoryRepository(application);
        allBarangHistories = repository.getAllBarangHistores();
    }

    public LiveData<List<BarangHistory>> getBarangHistory(int idBarang){
        return repository.getBarangHistory(idBarang);
    }

    public void insert(BarangHistory barangHistory){
        repository.insert(barangHistory);
    }

    public void update(BarangHistory barangHistory){
        repository.update(barangHistory);
    }

    public void delete(BarangHistory barangHistory){
        repository.delete(barangHistory);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<BarangHistory>> getAllBarangHistories() {
        return allBarangHistories;
    }
}
