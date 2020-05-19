package com.kasir.ui.transaksiDetail;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kasir.data.database.transaksiDetail.TransaksiDetailDao;
import com.kasir.data.database.transaksiDetail.TransaksiDetailDatabase;
import com.kasir.data.model.TransaksiDetail;

import java.util.List;

public class TransaksiDetailRepository {
    private TransaksiDetailDao transaksiDetailDao;
    private LiveData<List<TransaksiDetail>> getAllTransaksiDetail;
    private LiveData<List<TransaksiDetail>> getTranDet;

    public TransaksiDetailRepository(Application application){
        TransaksiDetailDatabase database = TransaksiDetailDatabase.getInstance(application);
        transaksiDetailDao = database.transaksiDetailDao();
        getAllTransaksiDetail = transaksiDetailDao.getAllTranDetail();
    }

    public void insert(TransaksiDetail transaksiDetail){
        new InsertBarangAsyncTask(transaksiDetailDao).execute(transaksiDetail);
    }

    public void update(TransaksiDetail transaksiDetail){
        new UpdateBarangAsyncTask(transaksiDetailDao).execute(transaksiDetail);
    }

    public void delete(TransaksiDetail transaksiDetail){
        new DeleteBarangAsyncTask(transaksiDetailDao).execute(transaksiDetail);
    }

    public void deleteAll(){
        new DeleteAllBarangAsyncTask(transaksiDetailDao).execute();
    }

    public LiveData<List<TransaksiDetail>> getGetAllTransaksiDetail(){
        return getAllTransaksiDetail;
    }
    public LiveData<List<TransaksiDetail>> getTranDet(int idTran){
        getTranDet = transaksiDetailDao.getTranDet(idTran);
        return getTranDet;
    }

    private static class InsertBarangAsyncTask extends AsyncTask<TransaksiDetail, Void, Void> {
        private TransaksiDetailDao transaksiDetailDao;

        private InsertBarangAsyncTask(TransaksiDetailDao transaksiDetailDao){
            this.transaksiDetailDao = transaksiDetailDao;
        }

        @Override
        protected Void doInBackground(TransaksiDetail... transaksiDetails) {
            transaksiDetailDao.insert(transaksiDetails[0]);
            return null;
        }
    }

    private static class UpdateBarangAsyncTask extends AsyncTask<TransaksiDetail, Void, Void> {
        private TransaksiDetailDao transaksiDetailDao;

        private UpdateBarangAsyncTask(TransaksiDetailDao transaksiDetailDao){
            this.transaksiDetailDao = transaksiDetailDao;
        }

        @Override
        protected Void doInBackground(TransaksiDetail... transaksiDetails) {
            transaksiDetailDao.update(transaksiDetails[0]);
            return null;
        }
    }

    private static class DeleteBarangAsyncTask extends AsyncTask<TransaksiDetail, Void, Void> {
        private TransaksiDetailDao transaksiDetailDao;

        private DeleteBarangAsyncTask(TransaksiDetailDao transaksiDetailDao){
            this.transaksiDetailDao = transaksiDetailDao;
        }

        @Override
        protected Void doInBackground(TransaksiDetail... transaksiDetails) {
            transaksiDetailDao.delete(transaksiDetails[0]);
            return null;
        }
    }

    private static class DeleteAllBarangAsyncTask extends AsyncTask<Void, Void, Void> {
        private TransaksiDetailDao transaksiDetailDao;

        private DeleteAllBarangAsyncTask(TransaksiDetailDao transaksiDetailDao){
            this.transaksiDetailDao = transaksiDetailDao;
        }

        @Override
        protected Void doInBackground(Void... transaksiDetails) {
            transaksiDetailDao.deleteAll();
            return null;
        }
    }
}
