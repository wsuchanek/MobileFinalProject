package io.sars.maps4;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.preference.PreferenceManager;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

/*************************************************************************************************
 * File:   MapsActivity.java
 * Author: Nicole Lane, Will Suchanek
 *
 *
 * Purpose: This activity allows the user to add markers for beverages that they have purchased at their current location (with information about the product).
 * It also allows the user to access a main menu.
 *
 * Algorithm:
 *      If user taps menu button, start menu activity.
 *      If user taps add marker, start Survey Activity.
 *      If user taps update map, make sure all markers are currently up to date.
 *      If user taps preferencecs, start preference activity.
 *      Allow user to scroll on the map and see all markers currently placed.
 *
 *************************************************************************************************/

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String mLatitudeText;
    private String mLongitudeText;

    DBAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Create an instance of GoogleAPIClient.
        // This is necessary to connect to Google services such as maps
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * Use the lifecycle methods to connect/disconnect to google services.
     * Necessary for using maps
     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            //showMissingPermissionError();
            Log.v("onResumeFragments", "Permission was denied");
            mPermissionDenied = false;
        }
        Toast.makeText(this, "Press Update Map!", Toast.LENGTH_SHORT).show();
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
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(this);
        enableMyLocation();
    }

    /**
     * This is the callback method for the mapClickListener
     * @param point the place on the map that was clicked.  Contains latitude and longitude
     */
    @Override
    public void onMapClick(LatLng point) {
        //Toast.makeText(this, "Map clicked", Toast.LENGTH_SHORT).show();
        Log.v("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]");
        //Do your stuff with LatLng here
        //Then pass LatLng to other activity
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
            /*
            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            */
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            Log.v("enableMyLocation: ", "Permission granted");
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        Log.v("MapsActivity:", "Map click detected");
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //LatLng sydney = new LatLng(-34, 151);
        LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        //mMap.clear();
        mMap.addMarker(new MarkerOptions().position(myLoc).title("You are here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        return false;
    }

    /**
     * These next three methods are part of the interface that must be implemented to
     * connect to google services.  We're basically connecting to google to use their API
     * including maps
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {
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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int result) {
        Log.v("Connection Suspended: ", " " + result);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.v("Connection Failed ", result.getErrorMessage());
    }

    /**
     *  These next methods are used to obtain permissions for using maps
     *
     **/
    @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    //                                       @NonNull int[] grantResults) {
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            Log.v("onRequestPermRslt:  ", "enabled permissions");
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            Log.v("onRequestPermRslt:  ", "DISABLED permissions");
        }
    }

    /**
     * Checks if the result contains a {@link PackageManager#PERMISSION_GRANTED} result for a
     * permission from a runtime permissions request.
     *
     * @see android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    //Get current latitude and longitude and send to survey activity; linked to add marker button
    public void putMarker(View view){
        Intent surveyIntent = new Intent(this, SurveyActivity.class);
        surveyIntent.putExtra("latitude", mLatitudeText);
        surveyIntent.putExtra("longitude", mLongitudeText);
        startActivity(surveyIntent);
    }

    //Go to menu activity; linked to menu button
    public void toMenu(View view){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        menuIntent.putExtra("latitude", mLatitudeText);
        menuIntent.putExtra("longitude", mLongitudeText);
        startActivity(menuIntent);
    }

    //Go to preferences; linked to preferences button
    public void toPreference(View view){
        Intent i = new Intent(this, MyPreferenceActivity.class);
        startActivity(i);

    }

    //Initialize map with current entries in the database
    public void setMap(View view){
        mMap.clear();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String ptypePref = SP.getString("ptypePref", "1");
        //Toast.makeText(this, ptypePref, Toast.LENGTH_SHORT).show();
        String ltypePref = SP.getString("ltypePref", "1");
        //Toast.makeText(this, ltypePref, Toast.LENGTH_SHORT).show();
        if (ptypePref.equals("1")) {
            displayAll(ltypePref);
        } else if (ptypePref.equals("2")){
            displayBeer(ltypePref);
        } else{
            displayWine(ltypePref);
        }
    }

    //Show all current entries in the database on the map
    public void displayAll(String ltp){
        //Toast.makeText(this, "Display All", Toast.LENGTH_SHORT).show();
        displayBeer(ltp);
        displayWine(ltp);
    }

    //Filter through all entries in the database and only display entries with "beer" as their TYPE.
    public void displayBeer(String ltp){
        database = new DBAdapter(this);
        database.open();
        int totalProducts = database.getProfilesCount();
        for (int n = 1; n <= totalProducts; n++) {
            Cursor c = database.getProduct(n);

            if (c.getString(1).equals("Beer")){
                if (ltp.equals("3")){
                    //Toast.makeText(this, "Beer Shop", Toast.LENGTH_SHORT).show();
                    if (c.getString(3).equals("Shop")){
                        LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                        String title = "Product Name: "+ c.getString(0) + "\n"+
                                "Product Type: " + c.getString(1) + "\n" +
                                "Location Name: " + c.getString(2) + "\n" +
                                "Location Type:  " + c.getString(3) + "\n";
                        if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                        if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                        mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));
                    }

                }else if(ltp.equals("2")) {
                    //Toast.makeText(this, "Beer Bar", Toast.LENGTH_SHORT).show();
                    if(c.getString(3).equals("Bar")){
                        LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                        String title = "Product Name: "+ c.getString(0) + "\n"+
                                "Product Type: " + c.getString(1) + "\n" +
                                "Location Name: " + c.getString(2) + "\n" +
                                "Location Type:  " + c.getString(3) + "\n";
                        if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                        if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                        mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));

                    }

                } else{
                    //Toast.makeText(this, "Beer All", Toast.LENGTH_SHORT).show();
                    LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                    String title = "Product Name: "+ c.getString(0) + "\n"+
                            "Product Type: " + c.getString(1) + "\n" +
                            "Location Name: " + c.getString(2) + "\n" +
                            "Location Type:  " + c.getString(3) + "\n";
                    if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                    if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                    mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));
                }
            }
        }
        database.close();
    }

    //Filter through all entries in the database, and only display those with "wine" in their TYPE field.
    public void displayWine(String ltp){
        database = new DBAdapter(this);
        database.open();
        int totalProducts = database.getProfilesCount();
        //Toast.makeText(this, Integer.toString(totalProducts), Toast.LENGTH_SHORT).show();
        for (int n = 1; n <= totalProducts; n++) {

            Cursor c = database.getProduct(n);
            //Toast.makeText(this, Integer.toString(n)+" "+c.getString(1)+" "+ltp+" "+c.getString(3), Toast.LENGTH_SHORT).show();
            if (c.getString(1).equals("Wine")){
                if (ltp.equals("3")){
                    //Toast.makeText(this, "Wine Shop", Toast.LENGTH_SHORT).show();
                    if (c.getString(3).equals("Shop")){
                        LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                        String title = "Product Name: "+ c.getString(0) + "\n"+
                                "Product Type: " + c.getString(1) + "\n" +
                                "Location Name: " + c.getString(2) + "\n" +
                                "Location Type:  " + c.getString(3) + "\n";
                        if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                        if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                        mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));
                    }

                }else if(ltp.equals("2")) {
                    //Toast.makeText(this, "Wine Bar", Toast.LENGTH_SHORT).show();
                    if(c.getString(3).equals("Bar")){
                        LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                        String title = "Product Name: "+ c.getString(0) + "\n"+
                                "Product Type: " + c.getString(1) + "\n" +
                                "Location Name: " + c.getString(2) + "\n" +
                                "Location Type:  " + c.getString(3) + "\n";
                        if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                        if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                        mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));

                    }

                } else{
                    //Toast.makeText(this, "Wine All", Toast.LENGTH_SHORT).show();
                    LatLng myLoc = new LatLng(c.getDouble(5), c.getDouble(6));
                    String title = "Product Name: "+ c.getString(0) + "\n"+
                            "Product Type: " + c.getString(1) + "\n" +
                            "Location Name: " + c.getString(2) + "\n" +
                            "Location Type:  " + c.getString(3) + "\n";
                    if (c.getDouble(4)!= -1.00){title+="Price: "+ Double.toString(c.getDouble(4))+ "\n";}
                    if (!c.getString(7).equals("")){title+="Additional Info: \n"+c.getString(7);}

                    mMap.addMarker(new MarkerOptions().position(myLoc).title(c.getString(0)).snippet(title));
                }
            }
        }
        database.close();
    }

    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) { //Display a toast when the user taps on a marker
        String toToast = marker.getSnippet();
        Toast.makeText(this, toToast, Toast.LENGTH_LONG).show();
        return false;
    }
}