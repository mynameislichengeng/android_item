package com.xguanjia.hx.badges;

import android.content.Context;

import java.lang.ref.SoftReference;

public class ABadgeUtil {
    static final String TAG = "ABadgeUtil";

    private static SoftReference<ABadgerProvider> aBadgerProvider = new SoftReference<ABadgerProvider>(null);

    private static ABadgerProvider getBadgerProvider(Context context) {
        if (aBadgerProvider.get() == null) {
            if (SamsungBadger.isAvailable(context))
                aBadgerProvider = new SoftReference<ABadgerProvider>(new SamsungBadger(context));
            else if (SonyXPeriaBadger.isAvailable(context))
                aBadgerProvider = new SoftReference<ABadgerProvider>(new SonyXPeriaBadger(context));
            else if (ApexBadger.isAvailable(context))
                aBadgerProvider = new SoftReference<ABadgerProvider>(new ApexBadger(context));
            else
                aBadgerProvider = new SoftReference<ABadgerProvider>(new SilentBadger(context));
        }

        return aBadgerProvider.get();
    }

    public static boolean setBadge(Context context, int count) {
        return getBadgerProvider(context.getApplicationContext()).setBadge(count);
    }

    public static boolean removeBadge(Context context) {
        return getBadgerProvider(context.getApplicationContext()).removeBadge();
    }
}
