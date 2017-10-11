package com.sdl.app.donate;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by vikas on 6/10/17.
 */

public class MyLocListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            Log.e("Latitude : ", location.getLatitude() + "" );
            Log.e("LOngitude : ", location.getLongitude() + "" );
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
