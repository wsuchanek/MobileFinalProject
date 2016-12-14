package io.sars.maps4;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*************************************************************************************************
 * File:   ListActivity.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Purpose: This activity displays all currently entered beverages from the database in a scrollable list view.
 *
 * Algorithm:
 *      Create a markerList object that allows us to get string values for all fields of all entries in the database.
 *      Add the BevFragment to the layout.
 *
 *************************************************************************************************/

public class ListActivity extends Activity {
    MarkerList markerlist;
    DBAdapter database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        database = new DBAdapter(this);

        markerlist = new MarkerList(this); //Use the marker list to get string values from an array that are currently in the database
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new BevFragment())
                    .commit();

        }


    }
    public void goBack(View view){finish();}

}
