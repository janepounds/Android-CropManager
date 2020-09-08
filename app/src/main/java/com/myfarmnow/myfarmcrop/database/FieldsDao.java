package com.myfarmnow.myfarmcrop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FieldsDao {
    @Insert
    void insert(CropField fieldsTable);

    @Query("SELECT * FROM CropField WHERE field_name LIKE :name")
    CropField getFields(String name);

    @Update
    void UpdateField(CropField fieldsTable);

    @Query("SELECT * FROM CropField")
    List<CropField> getAll();

    @Delete
    void delete(CropField fieldsTable);
}
