package com.xguanjia.hx.badges;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class ApexBadger extends ABadgerProvider {
    private static final String BROADCAST_ACTION = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String BROADCAST_PERMISSION = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String EXTRA_NOTIFY_PACKAGE = "package";
    private static final String EXTRA_NOTIFY_CLASS = "class";
    private static final String EXTRA_NOTIFY_COUNT = "count";

    protected ApexBadger(Context context) {
        super(context);
    }

    @Override
    boolean setBadge(int count) {
        Intent intent = generateIntent();
        intent.putExtra(EXTRA_NOTIFY_COUNT, count);

        context.sendBroadcast(intent);

        return true;
    }

    @Override
    boolean removeBadge() {
        Intent intent = generateIntent();
        intent.putExtra(EXTRA_NOTIFY_COUNT, 0);

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
            Log.d(TAG, String.format("Apex launcher was found, but your apps missing %s permission", BROADCAST_PERMISSION));
            return false;
        }

        return true;
    }
}
