package com.myfarmnow.myfarmcrop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyProduce.class,FieldsTable.class,CropsTable.class}, version = 1)
public abstract class MyFarmRoomDatabase extends RoomDatabase {

    public abstract MyProduceDao myProduceDao();
    public abstract FieldsDao fieldsDao();
    public abstract CropsDao cropsDao();

    private static MyFarmRoomDatabase produceDB;

    public static MyFarmRoomDatabase getInstance(Context context) {
        if (null == produceDB) {
            produceDB = buildDatabaseInstance(context);
        }
        return produceDB;
    }

    private static MyFarmRoomDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, MyFarmRoomDatabase.class, "produce.db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        produceDB = null;
    }

}

