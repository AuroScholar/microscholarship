package com.auro.scholr.util;

import android.util.Log;

public class AppLogger {

    private static boolean enableLogger = false;

    private AppLogger() {

    }


    public static void w(final String TAG, final String MSG) {

        if (enableLogger) {
            Log.w(TAG, MSG);
        }
    }

    public static void d(final String TAG, final String MSG) {
        if (enableLogger) {
            Log.d(TAG, MSG);
        }
    }

    public static void e(final String TAG, final String MSG) {
        if (enableLogger) {
            Log.e(TAG, MSG);
        }
    }

    public static void i(final String TAG, final String MSG) {
        if (enableLogger) {
            Log.i(TAG, MSG);
        }
    }

    public static void v(final String TAG, final String MSG) {
        if (enableLogger) {
            Log.v(TAG, MSG);
        }
    }

    public static void wtf(final String TAG, final String MSG) {
        if (enableLogger) {
            Log.wtf(TAG, MSG);
        }
    }
}
