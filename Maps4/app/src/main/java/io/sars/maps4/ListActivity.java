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
/**
 * Created by NicoleLane on 12/13/16.
 */

public class ListActivity extends Activity {
    MarkerList markerlist;
    Marker dummyMarker1;
    Marker dummyMarker2;
    Marker dummyMarker3;
    DBAdapter database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        database = new DBAdapter(this);


        /*database.open();
        int profile_counts = database.getProfilesCount(); //Get current number of things in the table and give this marker the next row
        dummyMarker1 = new Marker(1.11, 2.22, profile_counts+1);
        dummyMarker2 = new Marker(3.33, 4.44, profile_counts+2);
        dummyMarker3 = new Marker(5.55, 6.66, profile_counts+3);
        database.insertProduct("dummy1", "beer", "placeONE", "bar", 1.00, dummyMarker1.latitude, dummyMarker1.longitude, "soEXTRAone");
        database.insertProduct("dummy2", "wine", "placeTWO", "shop", 2.00, dummyMarker2.latitude, dummyMarker2.longitude, "soEXTRAtwo");
        database.insertProduct("dummy3", "beer", "placeTHREE", "bar", 3.00, dummyMarker3.latitude, dummyMarker3.longitude, "soEXTRAthree");

        database.close();*/

        markerlist = new MarkerList(this); //Use the marker list to get string values from an array that are currently in the database
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new BevFragment())
                    .commit();

        }


    }
    public void goBack(View view){finish();}

}
