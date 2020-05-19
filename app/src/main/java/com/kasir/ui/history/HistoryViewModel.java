package com.kasir.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kasir.data.model.History;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository repository;
    private LiveData<List<History>> getAllHistory;
    private LiveData<History> getId;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
        getAllHistory = repository.getGetAllHistory();
        getId = repository.getId();
    }

    public LiveData<History> getId(){
        return getId;
    }

    public LiveData<List<History>> getGetAllHistory(){
        return getAllHistory;
    }

    public void insert(History history){
        repository.insert(history);
    }

    public void update(History history){
        repository.update(history);
    }

    public void delete(History history){
        repository.delete(history);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
