package com.myfarmnow.myfarmcrop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyProduce.class}, version = 1)
public abstract class MyProduceDatabase extends RoomDatabase {

    public abstract MyProduceDao myProduceDao();

    private static MyProduceDatabase produceDB;

    public static MyProduceDatabase getInstance(Context context) {
        if (null == produceDB) {
            produceDB = buildDatabaseInstance(context);
        }
        return produceDB;
    }

    private static MyProduceDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, MyProduceDatabase.class, "produce.db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        produceDB = null;
    }

}

