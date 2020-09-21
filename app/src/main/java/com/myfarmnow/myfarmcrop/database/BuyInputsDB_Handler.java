package com.myfarmnow.myfarmcrop.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myfarmnow.myfarmcrop.app.CropManagerApp;


/**
 * DB_Handler contains Database Version and Name and creates/upgrades all tables in the Database
 **/


public class BuyInputsDB_Handler extends SQLiteOpenHelper {

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "User_DB";


    public BuyInputsDB_Handler() {
        super(CropManagerApp.getContext(), DB_NAME, null, DB_VERSION);
    }


    
    //*********** Creating Database ********//
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating Tables
        db.execSQL(User_Info_BuyInputsDB.createTable());
        db.execSQL(User_Recents_BuyInputsDB.createTable());
        db.execSQL(User_Cart_BuyInputsDB.createTableCart());
        db.execSQL(User_Cart_BuyInputsDB.createTableCartAttributes());
    }


    
    //*********** Upgrading Database ********//
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + User_Info_BuyInputsDB.TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + User_Recents_BuyInputsDB.TABLE_RECENTS);
        db.execSQL("DROP TABLE IF EXISTS " + User_Cart_BuyInputsDB.TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + User_Cart_BuyInputsDB.TABLE_CART_ATTRIBUTES);

        // Create tables again
        onCreate(db);
    }

}
