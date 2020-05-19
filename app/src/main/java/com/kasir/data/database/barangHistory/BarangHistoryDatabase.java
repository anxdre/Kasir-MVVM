package com.kasir.data.database.barangHistory;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kasir.data.model.BarangHistory;

@Database(entities = {BarangHistory.class}, version = 1)
public abstract class BarangHistoryDatabase extends RoomDatabase{

    public static BarangHistoryDatabase instance;

    public abstract BarangHistoryDao barangHistoryDao();

    public static synchronized BarangHistoryDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BarangHistoryDatabase.class, "barang_history_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulatedDBAsynTask extends AsyncTask<Void, Void, Void> {
        private BarangHistoryDao barangHistoryDao;
        private PopulatedDBAsynTask(BarangHistoryDatabase db){
            barangHistoryDao = db.barangHistoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
