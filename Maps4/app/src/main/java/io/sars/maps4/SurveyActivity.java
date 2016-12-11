package io.sars.maps4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);

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


    private RadioGroup.OnCheckedChangeListener borsListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup rbGroup, int radioId) {
            switch (radioId) {
                case 0x7f0b0074: // Bar button
                    //player.setLaneRate(Player.TOP);
                    break;
                case 0x7f0b0073: // Shop button
                    //player.setLaneRate(Player.MID);
                    break;
            }
        }
    };
    private RadioGroup.OnCheckedChangeListener borwListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup rbGroup, int radioId) {
            switch (radioId) {
                case 0x7f0b0070: // Beer Button
                    //player.setLaneRate(Player.TOP);
                    break;
                case 0x7f0b006f: // Wine button
                    //player.setLaneRate(Player.MID);
                    break;
            }
        }
    };

    public void placeMarker(View view){
        //code to add things to the database
        Marker marker = new Marker(long,lat,borw.)
    }

}
