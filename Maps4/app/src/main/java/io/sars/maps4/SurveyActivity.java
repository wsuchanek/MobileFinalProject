package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.database.Cursor;


import com.google.android.gms.location.LocationServices;

/**
 * github needs to work thanks
 * Created by willsuchanek on 12/10/16.
 */
public class SurveyActivity extends Activity {

    private RadioGroup bors;
    private RadioGroup borw;
    private EditText lnameET;
    private EditText pnameET;
    private EditText priceET;
    private EditText additionalET;

    private Marker marker;
    DBAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        database = new DBAdapter(this);


        /*Intent intent = getIntent();
        String latStr = intent.getStringExtra("latitude");
        String longStr = intent.getStringExtra("longitude");
        double lat = Double.parseDouble(latStr);
        double longitude = Double.parseDouble(longStr);*/
        int profile_counts = database.getProfilesCount(); //Get current number of things in the table and give this marker the next row
        marker = new Marker(11.11, 99.99, profile_counts);

        // TASK 4: INITIZLIZE UI OBJECTS AND VARIABLEs
        initialize();

        // TASK 5: REGISTER CHANGE LISTENERS
        registerChangeListener();

    }

    /*************************************************************************************************
     * Description: This function initialized all of the pointers and displays the first rating
     * Input: none
     * Output: none
     * Global Constants: none?
     *
     * Variables used: bors borw lnameET pnameET priceET additonalET
     *
     * Last Modified: 11/2/16
     *************************************************************************************************/
    private void initialize() {
        // TASK 5: GET REFERENCE TO EACH OF THE UI COMPONENTS
        bors = (RadioGroup) findViewById(R.id.barorshop);
        borw = (RadioGroup) findViewById(R.id.beerorwine);
        lnameET = (EditText) findViewById(R.id.lnameET);
        pnameET = (EditText) findViewById(R.id.pnameET);
        priceET = (EditText) findViewById(R.id.priceET);
        additionalET = (EditText) findViewById(R.id.additionalET);

        registerChangeListener();
    }

    /*************************************************************************************************
     * Description: Registers all the listeners being used
     * Input: none
     * Output: none
     * Global Constants: none?
     *
     * Variables used: borsListener borwListener
     *
     * Last Modified: 11/2/16
     *************************************************************************************************/
    private void registerChangeListener() {
        bors.setOnCheckedChangeListener(borsListener);
        borw.setOnCheckedChangeListener(borwListener);
    }

    //Is this marker for a bar or a shop?
    private RadioGroup.OnCheckedChangeListener borsListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup rbGroup, int radioId) {
            switch (radioId) {
                case R.id.bar: // Bar button
                    marker.setLtype(Marker.BAR);
                    break;
                case R.id.shop: // Shop button
                    marker.setLtype(Marker.SHOP);
                    break;
            }
        }
    };

    //Is this product beer or wine?
    private RadioGroup.OnCheckedChangeListener borwListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup rbGroup, int radioId) {
            switch (radioId) {
                case R.id.beer: // Beer Button
                    marker.setPtype(Marker.BEER);
                    break;
                case R.id.wine: // Wine button
                    marker.setPtype(Marker.WINE);
                    break;
            }
        }
    };

    //Put marker on the map
    public void placeMarker(View view){

        marker.setLname(lnameET.getText().toString());
        marker.setPname(pnameET.getText().toString());
        marker.setAdditionalInfo(additionalET.getText().toString());

        try {
            marker.setPrice(Double.parseDouble(priceET.getText().toString()));

        }
        catch (Exception e) {
            marker.setPrice(0.00);
        }


        database.open(); //open the database
        long id;
        id = database.insertProduct( //Insert the current information into the database
                marker.pname,
                marker.ptype,
                marker.lname,
                marker.ltype,
                marker.price,
                marker.latitude,
                marker.longitude,
                marker.additional

                );

        //---retrieve the same title to verify---
        Cursor c = database.getProduct(marker.getRowID());
        if (c.moveToFirst())
            displayProduct(c);
        else
            Toast.makeText(this, "No title found",
                    Toast.LENGTH_LONG).show();
        //-------------------
        database.close(); //Close it once information has been entered
        c.close();

        //code to add things to the database
    }

    public void displayProduct(Cursor c)
    {
        Toast.makeText(this,
                "product name: " + c.getString(0) + "\n" +
                        "product type: " + c.getString(1) + "\n" +
                        "location name: " + c.getString(2) + "\n" +
                        "location type:  " + c.getString(3) + "\n" +
                        "price:   " + c.getDouble(4) + "\n" +
                        "latitude:   " + c.getDouble(5) + "\n" +
                        "longitude:   " + c.getDouble(6) + "\n" +
                        "extras:    " +  c.getString(7),
                Toast.LENGTH_LONG).show();
    }

}
