package com.xguanjia.hx.badges;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SamsungBadger extends ABadgerProvider {
    private static final String PERMISSION_WRITE = "com.sec.android.provider.badge.permission.WRITE";
    private static final String PERMISSION_READ = "com.sec.android.provider.badge.permission.READ";

    private static final Uri CONTENT_URI = Uri.parse("content://com.sec.badge/apps");
    private static final String COLUMN_PACKAGE = "package";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_BADGECOUNT = "badgecount";

    protected SamsungBadger(Context context) {
        super(context);
    }

    @Override
    boolean setBadge(int count) {
        ContentValues cv = getContentValues();
        cv.put(COLUMN_BADGECOUNT, count);

        if (hasBadge())
            context.getContentResolver().update(CONTENT_URI, cv, "package=?", new String[] { getPackageName() });
        else
            context.getContentResolver().insert(CONTENT_URI, cv);

        return true;
    }

    @Override
    boolean removeBadge() {
        if (!hasBadge())
            return true;

        context.getContentResolver().delete(CONTENT_URI, "package=?", new String[] { getPackageName() });

        return true;
    }

    private ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PACKAGE, getPackageName());
        cv.put(COLUMN_CLASS, getLauncherClass());

        return cv;
    }

    private boolean hasBadge() {
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, "package=?", new String[] { getPackageName() }, null);

        return cursor != null && cursor.getCount() > 0;
    }

    static boolean isAvailable(Context context) {
        PackageManager pm = context.getPackageManager();

        if (pm.checkPermission(PERMISSION_WRITE, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, String.format("You don't have %s permission", PERMISSION_WRITE));
            return false;
        }

        if (pm.checkPermission(PERMISSION_READ, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, String.format("You don't have %s permission", PERMISSION_READ));
            return false;
        }

        return context.getContentResolver().query(CONTENT_URI, null, null, null, null) != null;
    }
}
