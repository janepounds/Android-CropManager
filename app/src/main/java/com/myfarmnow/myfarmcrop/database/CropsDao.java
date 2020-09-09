package com.myfarmnow.myfarmcrop.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;

import java.util.List;

@Dao
public interface CropsDao {
    @Insert
    void insert(Crop cropsTable);

    @Query("SELECT * FROM Crop WHERE id LIKE :id")
    Crop getProduce(int id);



    @Query("SELECT * FROM Crop")
    List<Crop> getAll();

    @Delete
    void delete(Crop cropsTable);
}
