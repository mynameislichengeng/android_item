package com.xguanjia.hx.badges;

import android.content.Context;

abstract class ABadgerProvider {
    final static String TAG = ABadgeUtil.TAG;

    final Context context;

    protected ABadgerProvider(Context context) {
        this.context = context;
    }

    abstract boolean setBadge(int count);
    abstract boolean removeBadge();

    String getPackageName() {
        return context.getPackageName();
    }

    String getLauncherClass() {
        return context.getPackageManager().getLaunchIntentForPackage(getPackageName()).getComponent().getClassName();
    }
}
