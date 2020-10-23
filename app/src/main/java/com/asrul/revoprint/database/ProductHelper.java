package com.asrul.revoprint.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_TABLE_NAME;

public class ProductHelper {
    private static DatabaseHelper databaseHelper;
    private static ProductHelper INSTANCE;
    private static SQLiteDatabase database;

    private ProductHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static ProductHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(PRODUCT_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(PRODUCT_TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(PRODUCT_TABLE_NAME, null, values);
    }

    public int deleteById(String id) {
        return database.delete(PRODUCT_TABLE_NAME, PRODUCT_ID + " = ?", new String[]{id});
    }

    public boolean checkProduct(String id) {
        database = databaseHelper.getWritableDatabase();
        String select = "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + PRODUCT_ID + " =?";
        Cursor cursor = database.rawQuery(select, new String[]{id});
        boolean checkProduct = false;
        if (cursor.moveToFirst()) {
            checkProduct = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d("TAG", String.format("%d records found", count));
        }
        cursor.close();
        return checkProduct;
    }
}
