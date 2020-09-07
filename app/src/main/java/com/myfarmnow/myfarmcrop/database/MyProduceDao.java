package com.myfarmnow.myfarmcrop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.util.List;

@Dao
public interface MyProduceDao {
    @Insert
    void insert(MyProduce myProduce);

    @Query("SELECT * FROM myproduce WHERE id LIKE :id")
    MyProduce getProduce(int id);

    @Query("SELECT * FROM myproduce")
    List<MyProduce> getAll();

    @Delete
    void delete(MyProduce myProduce);
}
