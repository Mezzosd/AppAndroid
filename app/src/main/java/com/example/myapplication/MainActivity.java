package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient client;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = LocationServices.getFusedLocaltionProviderClient(activity:this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context:
        this);
        switch (erroCode) {
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                Log.d(tag:"teste", msg:"dhow dialog");
                GoogleApiAvailability.getInstance().getErrorDialog(activity:this, erroCode, i1, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            }).show();
                break;
            case ConnectionResult.SUCCESS:
                Log.d(tag:"teste", msg:"esta atualizado");
                break;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.i(tag:"TEste".msg:
                            location.getLatitude() + "" + location.getLongitude());
                        } else {
                            Log.i(tag:"TEste", msg:"null");
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        LocationRequest locationRequest = LocationRequest.create() {
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(activity:this);
        SettingsClient.checkLocationSettings(builder.build())
               .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                   @Override
                   public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                   }
                   })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       if (e instanceof ResolvableApiException){
                           ResolvableApiException resolvable = (ResolvableApiException) e;
                           try {
                               resolvable.startResolutionForResult(activity:MainActivity.this, i:10);
                               }catch (IntentSender.SendIntentException e1) {
                           }
                       }
                   }
               });

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void OnLocationResult(LocationResult locationResult) {

                for(Location location : locationResult.getLocations()){
                    Log.i(tag:"TEste2", msg:location.getLongitude() + "");
                }
                }
                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    Log.i(tag:"TEste", msg:locationAvailability.isLocationAvailable() + "");

                }


            };
            client.requestLocationUpdates(locationRequest, locationCallback, loop:null);

                    }
        }
    }
