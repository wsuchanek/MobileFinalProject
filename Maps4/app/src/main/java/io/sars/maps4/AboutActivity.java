package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

    }

    //This the back button listener
    public void goBack(View view){finish();}
}
