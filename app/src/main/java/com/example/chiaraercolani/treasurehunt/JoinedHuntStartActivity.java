package com.example.chiaraercolani.treasurehunt;

import android.*;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
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

    int detected_distance = 100;

    int position = 0;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("JoinedHuntStart Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    public interface MyDialogCloseListener
    {
        public void handleDialogClose(DialogInterface dialog);
    }

    MyDialogCloseListener closeListener   = new MyDialogCloseListener() {
        @Override
        public void handleDialogClose(DialogInterface dialog) {
            displayNextStep(currentStep);
        }
    };

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            if(ContextCompat.checkSelfPermission(JoinedHuntStartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

        //dialog to prompt user to enable the GPS provider
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_joined_hunt);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        HuntFileReader huntFileReader = new HuntFileReader(intent.getStringExtra("filename"));
        steps = huntFileReader.getSteps();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

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

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(16).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void displayStep(Step step){
        LatLng latLng = new LatLng(step.getLatitude(),step.getLongitude());
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(step.getName());
        if(position == steps.size()-1){
            options.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("end_marker",100,100)) );
        }else {
            options.icon(BitmapDescriptorFactory.defaultMarker());
        }
        options.draggable(false);
        stepMarker = mMap.addMarker(options);
    }

    private void displayNextStep(Step step){
        position=position +1;
        removeStep();
        if(position < steps.size()) {
            currentStep = steps.get(position);
            displayStep(currentStep);
        }else{
            Intent intent = new Intent();
            intent.setClass(JoinedHuntStartActivity.this, EndOfHuntActivity.class);
            startActivity(intent);
        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void removeStep(){
        mMap.clear();
    }

    private LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            if(ContextCompat.checkSelfPermission(JoinedHuntStartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (currentLocation != null) {
                    updateCameraPosition();
                }
                if (currentLocation!= null && stepMarker != null) {
                    distanceToStep = meterDistanceBetweenPoints(currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            stepMarker.getPosition().latitude,
                            stepMarker.getPosition().longitude);
                    if (distanceToStep > detected_distance) {
                        //Toast.makeText(JoinedHuntStartActivity.this, "Still " + ((int)distanceToStep) + " meters to go", Toast.LENGTH_SHORT).show();
                        //View header = (View)getLayoutInflater().inflate(R.layout., null);
                        TextView headerValue = (TextView) findViewById(R.id.distance_id);
                        headerValue.setText( "Still " + ((int)distanceToStep) + " meters to go" );

                        //listView.addHeaderView(header);
                        //listView.setAdapter(adapter);
                    } else if(position ==steps.size()-1) {
                        TextView headerValue = (TextView) findViewById(R.id.distance_id);
                        headerValue.setText( "You reached the final step!");
                    } else{
                        TextView headerValue = (TextView) findViewById(R.id.distance_id);
                        headerValue.setText( "You reached step "+ (position+1) );
                    }
                    if (distanceToStep < detected_distance) {
                        if (displayQuestionDialog == null) {
                            displayQuestionDialog = new DisplayQuestionDialog();

                        }
                        if (!displayQuestionDialog.isAdded()) {
                            displayQuestionDialog.show(getFragmentManager(), "Display question");
                            getFragmentManager().executePendingTransactions();
                            displayQuestionDialog.setCancelable(false);
                            displayQuestionDialog.setStep(currentStep);
                            displayQuestionDialog.getAnswer(currentStep);
                            //TODO call displayNextStep when the dialog is dismissed
                            displayQuestionDialog.DismissListner(closeListener);

                        }
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to quit?")
                .setMessage("All your progress will be lost")
                .setNegativeButton(android.R.string.cancel,  new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        JoinedHuntStartActivity.this.finish();
                    }
                })
                .create()
                .show();


    }

}