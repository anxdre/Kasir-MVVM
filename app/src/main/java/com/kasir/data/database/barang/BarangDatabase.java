package com.kasir.data.database.barang;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kasir.data.model.Barang;

@Database(entities = {Barang.class}, version = 1)
public abstract class BarangDatabase extends RoomDatabase {

    private static BarangDatabase instance;

    public abstract BarangDao barangDao();

    public static synchronized BarangDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BarangDatabase.class, "barang_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

    private static class PopulatedDBAsynTask extends AsyncTask<Void, Void, Void> {
        private BarangDao barangDao;
        private PopulatedDBAsynTask(BarangDatabase db){
            barangDao = db.barangDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
