package com.idroidwarz.application.travelalarm;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.content.Intent;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation {

    double latt , longg;

    private static final String TAG = "GeocodingLocation";

    public  void getAddressFromLocation(final String locationAddress,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List
                            addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        latt = address.getLatitude();
                        longg = address.getLongitude();
                        Message message = Message.obtain();
                        message.setTarget(handler);

                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + latt +"\n" + longg ;
                        bundle.putString("address", result);
                        message.setData(bundle);

                        message.sendToTarget();

                        Bundle latbundle = new Bundle();
                        latbundle.putString("Latdata", String.valueOf(latt));
                        message.setData(latbundle);
                        message.sendToTarget();



                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }
            }


        };
        thread.start();
    }




}