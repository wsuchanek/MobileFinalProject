package io.sars.maps4;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

/*************************************************************************************************
 * File:   MarkerList.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Purpose: This object opens the database and creates an array of Strings using all of the information stored for each entry. This is used
 * in the BevFragment and ListView files in order to create a scrollable listview.
 *
 *************************************************************************************************/

public class MarkerList {
    public String[] data; //Array of strings; from database
    DBAdapter database; //Pointer to current database

    public MarkerList (Context context) {

        database = new DBAdapter(context); //Point to the database
        init();

    }

    //initialize the array of strings
    public void init () {
        database.open(); //open the database
        int size = database.getProfilesCount();
        data = new String[size];

        if (size > 0) { //only check the database if there are entries in it
            for (int i = 1; i <= size; i++) {
                Cursor c = database.getProduct(i); //Get each product from the database

                if (c.moveToFirst()) {
                    data[i-1] = getString(c); //If it exists, turn it into a string and add it to the array of strings
                }
            }
        }
        else {
            data[0] = "No beverages recorded.";
        }

        database.close(); //Close it once information has been entered
    }

    //Return a string of information from the current spot in the database
    public String getString(Cursor c)
    {
        //Spaces and tabs are for formatting purposes
        String temp = "Product Name:\t\t\t\t\t     " + c.getString(0) + "\n";
        temp+= "Product Type:\t\t\t\t\t         " + c.getString(1) + "\n";
        temp += "Location Name:\t\t\t\t\t     " + c.getString(2) + "\n";
        temp += "Location Type:\t\t\t\t\t       " + c.getString(3) + "\n";
        temp += "Price:\t\t\t\t\t                       $" + Double.toString(c.getDouble(4)) + "\n";
        temp+= "Extra Information:\t\t\t\t  " + c.getString(7);

        return temp;

    }

}
