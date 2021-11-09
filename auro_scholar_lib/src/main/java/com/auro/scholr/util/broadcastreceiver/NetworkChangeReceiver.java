package com.auro.scholr.util.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.auro.scholr.core.common.Status;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    String TAG="NetworkChangeReceiver";
        @Override
        public void onReceive(Context context, Intent intent)
        {
            try
            {
                if (isOnline(context)) {
                    if(AppUtil.callBackListner!=null)
                    {
                        AppUtil.callBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.NO_INTERNET_BROADCAST,true));
                    }
                    AppLogger.e(TAG, "Online Connect Intenet ");
                } else {
                    if(AppUtil.callBackListner!=null) {
                        AppUtil.callBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.NO_INTERNET_BROADCAST, false));
                    }
                    AppLogger.e(TAG, "Conectivity Failure !!! ");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        private boolean isOnline(Context context) {
            try {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                //should check null because in airplane mode it will be null
                return (netInfo != null && netInfo.isConnected());
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            }
        }
}
