package com.myfarmnow.myfarmcrop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;

@Database(entities = {MyProduce.class, CropField.class,CropsTable.class}, version = 3)
public abstract class MyFarmRoomDatabase extends RoomDatabase {

    public abstract MyProduceDao myProduceDao();
    public abstract FieldsDao fieldsDao();
    public abstract CropsDao cropsDao();

    private static MyFarmRoomDatabase myfarmDB;

    public static MyFarmRoomDatabase getInstance(Context context) {
        if (null == myfarmDB) {
            //myfarmDB = buildDatabaseInstance(context);
            myfarmDB = Room.databaseBuilder(context.getApplicationContext(),
                    MyFarmRoomDatabase.class, "myfarmDB.db")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return myfarmDB;
    }

    private static MyFarmRoomDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, MyFarmRoomDatabase.class, "myfarmDB.db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp() {
        myfarmDB = null;
    }

}

