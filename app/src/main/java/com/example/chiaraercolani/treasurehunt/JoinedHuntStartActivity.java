package com.example.chiaraercolani.treasurehunt;

import android.*;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by chiaraercolani on 14/12/16.
 */

public class JoinedHuntStartActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private Marker stepMarker;
    private double distanceToStep;
    private ArrayList<Step> steps;
    private Step currentStep;
    private DisplayQuestionDialog displayQuestionDialog;



    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            if(ContextCompat.checkSelfPermission(JoinedHuntStartActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (currentLocation != null) {
                    updateCameraPosition();
                }
            }
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_hunt_start);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_joined_hunt);
        mapFragment.getMapAsync(this);
        Intent intent =getIntent();
        HuntFileReader huntFileReader = new HuntFileReader(intent.getStringExtra("filename"));
        steps = huntFileReader.getSteps();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

        }

        if(steps.size()>0) {
            currentStep = steps.get(0);
            displayStep(currentStep);
        } else {
            Toast.makeText(this, "No step to display", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateCameraPosition(){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(18).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void displayStep(Step step){
        LatLng latLng = new LatLng(step.getLatitude(),step.getLongitude());
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(step.getName());
        options.icon(BitmapDescriptorFactory.defaultMarker());
        options.draggable(false);
        stepMarker = mMap.addMarker(options);
    }


    private LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            if(ContextCompat.checkSelfPermission(JoinedHuntStartActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (currentLocation != null) {
                    updateCameraPosition();
                }

                distanceToStep = meterDistanceBetweenPoints(currentLocation.getLatitude(),
                        currentLocation.getLongitude(),
                        stepMarker.getPosition().latitude,
                        stepMarker.getPosition().longitude);
                if(distanceToStep < 10){
                    if(displayQuestionDialog==null) {
                        displayQuestionDialog = new DisplayQuestionDialog();
                    }
                    if (!displayQuestionDialog.isAdded()) {
                        displayQuestionDialog.show(getFragmentManager(), "Display question");
                        getFragmentManager().executePendingTransactions();
                        displayQuestionDialog.setStep(currentStep);
                    }
                }

            }
        }

        @Override
        public void onProviderDisabled(String provider){

        }

        @Override
        public void onProviderEnabled(String provider){};

        @Override
        public void onStatusChanged(String provider, int status, Bundle
                extras){};
    };


    private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }

}