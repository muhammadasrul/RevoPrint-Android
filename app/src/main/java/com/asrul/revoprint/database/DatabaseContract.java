package com.asrul.revoprint.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.asrul.revoprint";
    private static final String SCHEME = "content";

    public static final class FavProductColumns implements BaseColumns {
        public static final String PRODUCT_TABLE_NAME = "fav_product";
        public static final String PRODUCT_ID = "product_id";
        public static final String PRODUCT_NAME = "name";
        public static final String PRODUCT_IMAGE = "image";
        public static final String PRODUCT_PRICE = "price";
        public static final String PRODUCT_DESC = "desc";
        public static final String PRODUCT_JENIS = "jenis";

        public static final Uri PRODUCT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PRODUCT_TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
