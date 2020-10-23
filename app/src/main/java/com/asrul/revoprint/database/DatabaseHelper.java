package com.asrul.revoprint.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_DESC;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_ID;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_IMAGE;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_JENIS;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_NAME;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_PRICE;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_MOVIE_TABLE =
            String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER UNIQUE," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
                    PRODUCT_TABLE_NAME,
                    _ID,
                    PRODUCT_ID,
                    PRODUCT_NAME,
                    PRODUCT_PRICE,
                    PRODUCT_IMAGE,
                    PRODUCT_DESC,
                    PRODUCT_JENIS
            );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        onCreate(db);
    }
}
