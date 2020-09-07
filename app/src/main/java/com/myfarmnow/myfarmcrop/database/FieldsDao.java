package com.myfarmnow.myfarmcrop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FieldsDao {
    @Insert
    void insert(FieldsTable fieldsTable);

    @Query("SELECT * FROM fieldstable WHERE field_name LIKE :name")
    FieldsTable getProduce(String name);

    @Query("SELECT * FROM fieldstable")
    List<FieldsTable> getAll();

    @Delete
    void delete(FieldsTable fieldsTable);
}
