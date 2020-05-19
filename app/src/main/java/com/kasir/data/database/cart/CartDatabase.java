package com.kasir.data.database.cart;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kasir.data.model.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {
    private static CartDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized CartDatabase getInstance(Context context){
           if (instance == null){
               instance = Room.databaseBuilder(context.getApplicationContext(),
                       CartDatabase.class, "cart_database")
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
        private CartDao cartDao;
        private PopulatedDBAsynTask(CartDatabase db){
            cartDao = db.cartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
