package com.myfarmnow.myfarmcrop.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

@Database(entities = {MyProduce.class}, version = 1)
public abstract class MyFarmRoomDatabase extends RoomDatabase {

    public abstract MyProduceDao myProduceDao();

    private static MyFarmRoomDatabase myFarmDB;

    public static MyFarmRoomDatabase getInstance(Context context) {
        if (null == myFarmDB) {
            myFarmDB = Room.databaseBuilder(context.getApplicationContext(),
                    MyFarmRoomDatabase.class, "myFarmDB.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myFarmDB;
    }

    public void cleanUp() {
        myFarmDB = null;
    }

}
