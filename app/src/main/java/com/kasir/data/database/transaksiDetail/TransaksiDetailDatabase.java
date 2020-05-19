package com.kasir.data.database.transaksiDetail;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kasir.data.model.TransaksiDetail;

@Database(entities = {TransaksiDetail.class}, version = 1)
public abstract class TransaksiDetailDatabase extends RoomDatabase {
    private static TransaksiDetailDatabase instance;

    public abstract TransaksiDetailDao transaksiDetailDao();

    public static synchronized TransaksiDetailDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TransaksiDetailDatabase.class, "transaksi_detail_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulatedDBAsynTask extends AsyncTask<Void, Void, Void> {
        private TransaksiDetailDao transaksiDetailDao;
        private PopulatedDBAsynTask(TransaksiDetailDatabase db){
            transaksiDetailDao = db.transaksiDetailDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
