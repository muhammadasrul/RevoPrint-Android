package com.asrul.revoprint.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import static com.asrul.revoprint.database.DatabaseContract.AUTHORITY;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_TABLE_NAME;
import static com.asrul.revoprint.database.DatabaseContract.FavProductColumns.PRODUCT_URI;

public class MyContentProvider extends ContentProvider {

    private static final int PRODUCT = 1;
    private static final int PRODUCT_ID = 2;

    private ProductHelper productHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, PRODUCT_TABLE_NAME, PRODUCT);

        sUriMatcher.addURI(AUTHORITY, PRODUCT_TABLE_NAME + "/#", PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        productHelper = ProductHelper.getInstance(getContext());
        productHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                cursor = productHelper.queryAll();
                break;
            case PRODUCT_ID:
                cursor = productHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added = 0;
        Uri contentUri = null;
        if (sUriMatcher.match(uri) == PRODUCT) {
            added = productHelper.insert(values);
            if (added > 0) {
                contentUri = ContentUris.withAppendedId(PRODUCT_URI, added);
            }
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return contentUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted = 0;
        if (sUriMatcher.match(uri) == PRODUCT_ID) {
            deleted = productHelper.deleteById(uri.getLastPathSegment());
        }

        if (deleted > 0 ) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
