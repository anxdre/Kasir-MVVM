package com.kasir.ui.barang;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kasir.data.database.barang.BarangDao;
import com.kasir.data.database.barang.BarangDatabase;
import com.kasir.data.model.Barang;

import java.util.List;

public class BarangRepository {
    private BarangDao barangDao;
    private LiveData<List<Barang>> allBarangs;
    private LiveData<Barang> getId;

    public BarangRepository(Application application){
        BarangDatabase database = BarangDatabase.getInstance(application);
        barangDao = database.barangDao();
        allBarangs = barangDao.getAllBarangs();
        getId = barangDao.getId();
    }

    LiveData<Barang> getId(){
        return getId;
    }

    public void insert(Barang barang){
        new InsertBarangAsyncTask(barangDao).execute(barang);
    }

    public void update(Barang barang){
        new UpdateBarangAsynTask(barangDao).execute(barang);
    }

    public void delete(Barang barang){
        new DeleteBarangAsynTask(barangDao).execute(barang);
    }

    public LiveData<List<Barang>> getAllNotes(){
        return allBarangs;
    }

    private static class InsertBarangAsyncTask extends AsyncTask<Barang, Void, Void> {
        private BarangDao barangDao;

        private InsertBarangAsyncTask(BarangDao barangDao){
            this.barangDao = barangDao;
        }

        @Override
        protected Void doInBackground(Barang... barangs) {
            barangDao.insert(barangs[0]);
            return null;
        }
    }

    private static class UpdateBarangAsynTask extends AsyncTask<Barang, Void, Void> {
        private BarangDao barangDao;

        private UpdateBarangAsynTask(BarangDao barangDao){
            this.barangDao = barangDao;
        }

        @Override
        protected Void doInBackground(Barang... barangs) {
            barangDao.update(barangs[0]);
            return null;
        }
    }

    private static class DeleteBarangAsynTask extends AsyncTask<Barang, Void, Void> {
        private BarangDao barangDao;

        private DeleteBarangAsynTask(BarangDao barangDao){
            this.barangDao = barangDao;
        }

        @Override
        protected Void doInBackground(Barang... barangs) {
            barangDao.delete(barangs[0]);
            return null;
        }
    }
}
