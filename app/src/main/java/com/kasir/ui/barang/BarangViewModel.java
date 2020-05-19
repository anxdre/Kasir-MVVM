package com.kasir.ui.barang;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kasir.data.model.Barang;

import java.util.List;

public class BarangViewModel extends AndroidViewModel {
    private BarangRepository repository;
    private LiveData<List<Barang>> allBarangs;
    private LiveData<Barang> getId;

    public BarangViewModel(@NonNull Application application) {
        super(application);
        repository = new BarangRepository(application);
        allBarangs = repository.getAllNotes();
        getId = repository.getId();
    }

    public LiveData<Barang> getId(){
        return getId;
    }

    public void insert(Barang barang){
        repository.insert(barang);
    }

    public void update(Barang barang){
        repository.update(barang);
    }

    public void delete(Barang barang){
        repository.delete(barang);
    }

    public LiveData<List<Barang>> getAllBarangs(){
        return allBarangs;
    }
}
