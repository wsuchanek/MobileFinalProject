package io.sars.maps4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import android.database.Cursor;

/**
 * github needs to work thanks
 * Created by willsuchanek on 12/10/16.
 */
public class SurveyActivity extends Activity {

    private RadioGroup ltypeRG;
    private RadioGroup ptypeRG;
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


        Intent intent = getIntent();
        String latStr = intent.getStringExtra("latitude");
        String longStr = intent.getStringExtra("longitude");
        double lat = Double.parseDouble(latStr);
        double longitude = Double.parseDouble(longStr);
        int profile_counts = database.getProfilesCount(); //Get current number of things in the table and give this marker the next row
        marker = new Marker(lat, longitude, profile_counts+1);

        // TASK 4: INITIZLIZE UI OBJECTS AND VARIABLEs
        initialize();

        // TASK 5: REGISTER CHANGE LISTENERS
        registerChangeListener();
        lnameET.setText("");
        pnameET.setText("");
        additionalET.setText("");
        priceET.setText("0");
    }

    /*************************************************************************************************
     * Description: This function initialized all of the pointers and displays the first rating
     * Input: none
     * Output: none
     * Global Constants: none?
     *
     * Variables used: ltypeRG ptypeRG lnameET pnameET priceET additonalET
     *
     * Last Modified: 11/2/16
     *************************************************************************************************/
    private void initialize() {
        // TASK 5: GET REFERENCE TO EACH OF THE UI COMPONENTS
        ltypeRG = (RadioGroup) findViewById(R.id.barorshop);
        ptypeRG = (RadioGroup) findViewById(R.id.beerorwine);
        lnameET = (EditText) findViewById(R.id.lnameET);
        pnameET = (EditText) findViewById(R.id.pnameET);
        priceET = (EditText) findViewById(R.id.priceET);
        additionalET = (EditText) findViewById(R.id.additionalET);
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
        ltypeRG.setOnCheckedChangeListener(borsListener);
        ptypeRG.setOnCheckedChangeListener(borwListener);
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
        marker.setPrice(Double.parseDouble(priceET.getText().toString()));




        Toast.makeText(this, marker.getPname()+"|"+marker.getLname()+"|"+marker.getPtype()+"|"+marker.getLtype(), Toast.LENGTH_LONG).show();
        if (marker.getLname() != "" && marker.getPname() != "" && marker.getPtype() != "" && marker.getLtype() != "") {
            Toast.makeText(this, "Adding to database.", Toast.LENGTH_LONG).show();
            
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
                Toast.makeText(this, "No title found",Toast.LENGTH_LONG).show();
            //-------------------
            database.close(); //Close it once information has been entered
            c.close();
            finish();
        }
        else {
            Toast.makeText(this, "You did not fill out a required field.", Toast.LENGTH_LONG).show();
        }
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
