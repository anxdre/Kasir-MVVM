package com.kasir.ui.history;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kasir.data.database.history.HistoryDao;
import com.kasir.data.database.history.HistoryDatabase;
import com.kasir.data.model.History;

import java.util.List;

public class HistoryRepository {
    private HistoryDao historyDao;
    private LiveData<List<History>> getAllHistory;
    private LiveData<History> getId;

    public HistoryRepository(Application application){
        HistoryDatabase database = HistoryDatabase.getInstance(application);
        historyDao = database.historyDao();
        getAllHistory = historyDao.getAllHistory();
        getId = historyDao.getId();
    }

    public LiveData<History> getId(){
        return getId;
    }

    public void insert(History history){
        new InsertBarangAsyncTask(historyDao).execute(history);
    }

    public void update(History history){
        new UpdateBarangAsyncTask(historyDao).execute(history);
    }

    public void delete(History history){
        new DeleteBarangAsyncTask(historyDao).execute(history);
    }

    public void deleteAll(){
        new DeleteAllBarangAsyncTask(historyDao).execute();
    }

    public LiveData<List<History>> getGetAllHistory(){
        return getAllHistory;
    }

    private static class InsertBarangAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private InsertBarangAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.insert(histories[0]);
            return null;
        }
    }

    private static class UpdateBarangAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private UpdateBarangAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.update(histories[0]);
            return null;
        }
    }

    private static class DeleteBarangAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao historyDao;

        private DeleteBarangAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            historyDao.delete(histories[0]);
            return null;
        }
    }

    private static class DeleteAllBarangAsyncTask extends AsyncTask<Void, Void, Void> {
        private HistoryDao historyDao;

        private DeleteAllBarangAsyncTask(HistoryDao historyDao){
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(Void... histories) {
            historyDao.deleteAll();
            return null;
        }
    }
}
