package com.asrul.revoprint;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class FilePath {

    public static String getFilePath(Context context, Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocumentUri(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split("");
                String type=split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageState()+"/"+split[1];
                }
            }
            else if (isDownloadsDocumentsUri(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));

                return getDatacolumn(context, contentUri, null, null);
            }
            else if (isMediaDocumentUri(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                String selection = "_id=?";
                String[] selectionargs = new String[]{split[1]};

                return getDatacolumn(context, contentUri, selection, selectionargs);
            }
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
            {
                return uri.getLastPathSegment();
            }
            return getDatacolumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDatacolumn(Context context, Uri uri, String selection, String[] selectionargs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionargs, null);
            if (cursor!=null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally {
            if (cursor!=null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocumentUri(Uri uri) {
        return "com.android.extrenalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocumentsUri(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocumentUri(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.android.apps.photos.content".equals(uri.getAuthority());
    }
}
