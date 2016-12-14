package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.net.Inet4Address;

/*************************************************************************************************
 * File:   MenuActivity.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Purpose: This activity has several buttons that the user can tap to take them to different activities. They can make an entry into the
 * database, see a list of all current entries, change their preferences, and return to the map activity.
 *
 *************************************************************************************************/
public class MenuActivity extends Activity {
    private String lat;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Intent intent = getIntent();
        lat = intent.getStringExtra("latitude"); //Get latitude and longitude from previous map activity of user's current location
        longitude = intent.getStringExtra("longitude");

    }

    //Add a marker by going to survey activity
    public void toSurvey(View view){
        Intent surveyIntent = new Intent(this, SurveyActivity.class);
        surveyIntent.putExtra("latitude", lat);
        surveyIntent.putExtra("longitude", longitude);
        startActivity(surveyIntent);
    }
    //Start about activity; linked to about button
    public void toAbout(View view){
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }
    //Start List activity; linked to list button
    public void toListView(View view){
        Intent listIntent = new Intent(this, ListActivity.class);
        startActivity(listIntent);
    }
    //Return to map; linked to back button
    public void goBacktoMap(View view){finish();}
}
