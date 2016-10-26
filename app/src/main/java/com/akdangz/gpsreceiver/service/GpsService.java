package com.akdangz.gpsreceiver.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static com.akdangz.gpsreceiver.glob.Constants.*;

public class GpsService extends IntentService {
    public GpsService() {
        super("GPS_SERVICE");

        Log.d("GPS_SERVICE", "SERVICE STARTED");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Context mContext = this.getApplicationContext();

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Intent localIntent =
                        new Intent(BROADCAST_ACTION)
                                .putExtra(EXTENDED_DATA_STATUS, "hello from service!");

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(localIntent);
                Log.d("GPS_SERVICE", "BROADCAST SENT");
            }
        };

        Timer mTimer = new Timer();
        mTimer.schedule(mTask, 5000, 5000);
    }


}
