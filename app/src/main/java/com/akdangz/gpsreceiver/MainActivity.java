package com.akdangz.gpsreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.akdangz.gpsreceiver.glob.Constants;
import com.akdangz.gpsreceiver.service.GpsService;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    LocationManager mLocationManager;

    double mLat, mLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this.getApplicationContext();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            return;
        } else {
            IntentFilter mStatusIntentFilter = new IntentFilter(
                    Constants.BROADCAST_ACTION);

            GpsResponseReceiver mGpsResponseReceiver
                    = new GpsResponseReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    mGpsResponseReceiver,
                    mStatusIntentFilter);

            Intent mServiceIntent = new Intent(mContext, GpsService.class);
            mContext.startService(mServiceIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        IntentFilter mStatusIntentFilter = new IntentFilter(
                Constants.BROADCAST_ACTION);

        GpsResponseReceiver mGpsResponseReceiver
                = new GpsResponseReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mGpsResponseReceiver,
                mStatusIntentFilter);

        Intent mServiceIntent = new Intent(mContext, GpsService.class);
        mContext.startService(mServiceIntent);
    }

    private class GpsResponseReceiver extends BroadcastReceiver {
        private GpsResponseReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                mLat = location.getLatitude();
                mLon = location.getLongitude();

                Log.d("LATITUDE", Double.toString(mLat));
                Log.d("LONGITUDE", Double.toString(mLon));
            }
        }
    }
}
