package com.myfarmnow.myfarmcrop.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CropsDao {
    @Insert
    void insert(CropsTable cropsTable);

    @Query("SELECT * FROM cropstable WHERE id LIKE :id")
    CropsTable getProduce(int id);



    @Query("SELECT * FROM cropstable")
    List<CropsTable> getAll();

    @Delete
    void delete(CropsTable cropsTable);
}
