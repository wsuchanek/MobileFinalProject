package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/*************************************************************************************************
 * File:   AboutActivity.java
 * Author: Nicole Lane, Will Suchanek
 *
 *
 * Purpose: This file is the 'about' page activity. It displays a text box that describes the functionality of our app.
 *
 * Algorithm:
 * 1) start activity
 * 2) end activity when user clicks the back button
 *
 *
 *************************************************************************************************/
public class AboutActivity extends Activity{
    public TextView welcome;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        welcome = (TextView) findViewById(R.id.thankview);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = SP.getString("name", "");
        if (!name.equals("")){
            String setTo = "Thanks, "+name+", for using our app!";
            welcome.setText(setTo);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String bgColor = SP.getString("bgColor","1");
        if (bgColor.equals("1")){
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(AboutActivity.this, R.color.cabernet));
        } else if (bgColor.equals("2")){
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(AboutActivity.this, R.color.green));
        }else{
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(AboutActivity.this, R.color.white));
        }
    }

    //This the back button listener
    public void goBack(View view){finish();}
}
