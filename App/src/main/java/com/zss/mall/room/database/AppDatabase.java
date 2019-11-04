package com.zss.mall.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zss.mall.room.dao.CartDao;
import com.zss.mall.room.entity.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase database;

    public static AppDatabase getInstance(final Context context) {
        if (database == null) {
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "shop_mall").build();
                }
            }
        }
        return database;
    }

    public abstract CartDao getCartDao();

}