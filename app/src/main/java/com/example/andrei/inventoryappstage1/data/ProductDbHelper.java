package com.example.andrei.inventoryappstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PRODUCTS";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //the create table command
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " ("
                + ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductContract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductContract.ProductEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + ProductContract.ProductEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //no change of the database is required
    }
}
