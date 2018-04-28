package com.example.andrei.inventoryappstage1.data;

import android.provider.BaseColumns;

public class ProductContract {

    private ProductContract() {
    }

    public static final class ProductEntry implements BaseColumns {
        //table name
        public final static String TABLE_NAME = "products";

        //names of the columns of the table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product_name";
        public final static String COLUMN_PRICE = "price";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
    }
}
