package com.xguanjia.hx.badges;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class SonyXPeriaBadger extends ABadgerProvider {
    private static final String BROADCAST_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
    private static final String BROADCAST_PERMISSION = "com.sonyericsson.home.permission.BROADCAST_BADGE";

    private static final String EXTRA_NOTIFY_PACKAGE = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME";
    private static final String EXTRA_NOTIFY_CLASS = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME";
    private static final String EXTRA_NOTIFY_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";
    private static final String EXTRA_NOTIFY_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE";

    protected SonyXPeriaBadger(Context context) {
        super(context);
    }

    @Override
    boolean setBadge(int count) {
        Intent intent = generateIntent();

        intent.putExtra(EXTRA_NOTIFY_SHOW_MESSAGE, count > 0);
        intent.putExtra(EXTRA_NOTIFY_MESSAGE, String.valueOf(count));

        context.sendBroadcast(intent);

        return true;
    }

    @Override
    boolean removeBadge() {
        Intent intent = generateIntent();

        intent.putExtra(EXTRA_NOTIFY_SHOW_MESSAGE, false);

        context.sendBroadcast(intent);

        return true;
    }

    private Intent generateIntent() {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(EXTRA_NOTIFY_PACKAGE, getPackageName());
        intent.putExtra(EXTRA_NOTIFY_CLASS, getLauncherClass());

        return intent;
    }

    static boolean isAvailable(Context context) {
        PackageManager pm = context.getPackageManager();

        if (pm.queryBroadcastReceivers(new Intent(BROADCAST_ACTION), 0).size() == 0) {
            return false;
        }

        if (pm.checkPermission(BROADCAST_PERMISSION, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, String.format("Sont xperia launcher was found, but your apps missing %s permission", BROADCAST_PERMISSION));
            return false;
        }

        return true;
    }
}
