package com.example.andrei.inventoryappstage1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andrei.inventoryappstage1.data.ProductContract.ProductEntry;
import com.example.andrei.inventoryappstage1.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;
    TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add few lines in database description TextView
        displayView = findViewById(R.id.details_database);
        displayView.append(ProductEntry._ID + " - " +
                ProductEntry.COLUMN_PRODUCT_NAME + " - " +
                ProductEntry.COLUMN_PRICE + " - " +
                ProductEntry.COLUMN_QUANTITY + " - " +
                ProductEntry.COLUMN_SUPPLIER_NAME + " - " +
                ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");

        //initialize the db helper
        mDbHelper = new ProductDbHelper(this);

        //implement onClick listener for the button
        Button btn = findViewById(R.id.btn_insert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProduct();
            }
        });

        //display the database
        displayDatabaseInfo(true);
    }

    /**
     * Display the database details.
     *
     * @param k the type of display
     *          k = true, append to displayView all entries in db
     *          k = false, append to displayView last entry in db
     */
    private void displayDatabaseInfo(boolean k) {
        //set the db to read
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //make the mask for db
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER};

        //retrieve the results based on the mask applied to db
        Cursor cursor = db.query(ProductEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        try {
            //get the columns indexes
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            if (k == true) { //add all the entries from database
                while (cursor.moveToNext()) {
                    //get the actual value for the current entry
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    int currentPrice = cursor.getInt(priceColumnIndex);
                    int currentQuantity = cursor.getInt(quantityColumnIndex);
                    String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                    String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                    //append the values to result
                    displayView.append(("\n" + currentID + " - " +
                            currentName + " - " +
                            currentPrice + " - " +
                            currentQuantity + " - " +
                            currentSupplierName + " - " +
                            currentSupplierPhone));
                }
            } else {//add only last entry
                //get the actual value for the current entry
                cursor.moveToLast();
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                //append the values to result
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }
        } finally {
            //always close the cursor after using it. no performance leakage
            cursor.close();
        }
    }

    private void insertProduct() {
        TextView error = findViewById(R.id.error);

        try {
            error.setVisibility(View.GONE);
            //get the values from input fields
            String itemName = ((EditText) findViewById(R.id.insert_product_name)).getText().toString();
            int itemPrice = Integer.parseInt(((EditText) findViewById(R.id.insert_product_price)).getText().toString());
            int itemQuantity = Integer.parseInt(((EditText) findViewById(R.id.insert_product_quantity)).getText().toString());
            String itemSupplierName = ((EditText) findViewById(R.id.insert_supplier_name)).getText().toString();
            String itemSupplierPhone = ((EditText) findViewById(R.id.supplier_phone_number)).getText().toString();

            //set the db to write
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            //create kay-value paits
            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_PRODUCT_NAME, itemName);
            values.put(ProductEntry.COLUMN_PRICE, itemPrice);
            values.put(ProductEntry.COLUMN_QUANTITY, itemQuantity);
            values.put(ProductEntry.COLUMN_SUPPLIER_NAME, itemSupplierName);
            values.put(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, itemSupplierPhone);

            //insert the pairs to db.
            long result = db.insert(ProductEntry.TABLE_NAME, null, values);

            if(result == -1) throw new RuntimeException("Bad input");

            displayDatabaseInfo(false);
        } catch (Exception e) {
            error.setVisibility(View.VISIBLE);
        }
    }

}
