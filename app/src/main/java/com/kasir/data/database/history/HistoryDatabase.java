package com.kasir.data.database.history;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kasir.data.model.History;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    private static HistoryDatabase instance;

    public abstract HistoryDao historyDao();

    public static synchronized HistoryDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HistoryDatabase.class, "history_database")
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
        private HistoryDao historyDao;
        private PopulatedDBAsynTask(HistoryDatabase db){
            historyDao = db.historyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
