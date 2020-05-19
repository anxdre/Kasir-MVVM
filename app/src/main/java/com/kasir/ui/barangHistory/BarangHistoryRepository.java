package com.kasir.ui.barangHistory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kasir.data.database.barangHistory.BarangHistoryDao;
import com.kasir.data.database.barangHistory.BarangHistoryDatabase;
import com.kasir.data.model.BarangHistory;

import java.util.List;

public class BarangHistoryRepository {
    private BarangHistoryDao barangHistoryDao;
    private LiveData<List<BarangHistory>> allBarangHistores;
    private LiveData<List<BarangHistory>> getBarangHistory;

    public BarangHistoryRepository(Application application){
        BarangHistoryDatabase barangHistoryDatabase = BarangHistoryDatabase.getInstance(application);
        barangHistoryDao = barangHistoryDatabase.barangHistoryDao();
        allBarangHistores = barangHistoryDao.getAllBarangHistory();
    }

    public LiveData<List<BarangHistory>> getBarangHistory(int idBarang){
        getBarangHistory = barangHistoryDao.getBarangHis(idBarang);
        return getBarangHistory;
    }

    public void insert(BarangHistory barang){
        new InsertBarangAsyncTask(barangHistoryDao).execute(barang);
    }

    public void update(BarangHistory barang){
        new UpdateBarangAsynTask(barangHistoryDao).execute(barang);
    }

    public void delete(BarangHistory barang){
        new DeleteBarangAsynTask(barangHistoryDao).execute(barang);
    }

    public void deleteAll(){
        new DeleteAllBarangAsyncTask(barangHistoryDao).execute();
    }

    public LiveData<List<BarangHistory>> getAllBarangHistores(){
        return allBarangHistores;
    }

    private static class InsertBarangAsyncTask extends AsyncTask<BarangHistory, Void, Void> {
        private BarangHistoryDao barangHistoryDao;

        private InsertBarangAsyncTask(BarangHistoryDao barangHistoryDao){
            this.barangHistoryDao = barangHistoryDao;
        }

        @Override
        protected Void doInBackground(BarangHistory... barangs) {
            barangHistoryDao.insert(barangs[0]);
            return null;
        }
    }

    private static class UpdateBarangAsynTask extends AsyncTask<BarangHistory, Void, Void> {
        private BarangHistoryDao barangHistoryDao;

        private UpdateBarangAsynTask(BarangHistoryDao barangHistoryDao){
            this.barangHistoryDao = barangHistoryDao;
        }

        @Override
        protected Void doInBackground(BarangHistory... barangs) {
            barangHistoryDao.update(barangs[0]);
            return null;
        }
    }

    private static class DeleteBarangAsynTask extends AsyncTask<BarangHistory, Void, Void> {
        private BarangHistoryDao barangHistoryDao;

        private DeleteBarangAsynTask(BarangHistoryDao barangHistoryDao){
            this.barangHistoryDao = barangHistoryDao;
        }

        @Override
        protected Void doInBackground(BarangHistory... barangs) {
            barangHistoryDao.delete(barangs[0]);
            return null;
        }
    }

    private static class DeleteAllBarangAsyncTask extends AsyncTask<Void, Void, Void> {
        private BarangHistoryDao barangHistoryDao;

        private DeleteAllBarangAsyncTask(BarangHistoryDao barangHistoryDao){
            this.barangHistoryDao = barangHistoryDao;
        }

        @Override
        protected Void doInBackground(Void... carts) {
            barangHistoryDao.deleteAll();
            return null;
        }
    }
}
