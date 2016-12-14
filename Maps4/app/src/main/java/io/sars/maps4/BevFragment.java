package io.sars.maps4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;


import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.database.Cursor;


/*************************************************************************************************
 * File:   BevFragment.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Purpose:
 *
 * This file creates a fragment that gets displayed in the ListActivity.java file. It creates an arrayList of all information stored in the database by the user,
 * and puts them into an arrayList adapter. This adapter then gets displayed in the listView in the ListActivity.
 *
 * Algorithm:
 *      Get strings about database information from markerList class.
 *      Put those into an arrayList.
 *      Put all entries in the arrayList into a scrollable listView.
 *
 *************************************************************************************************/

public class BevFragment extends Fragment {
    MarkerList markerlist;
    String[] info;
    public BevFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        markerlist = new MarkerList(getActivity());
        info = markerlist.data;

        List<String> productInfo = new ArrayList<String>(Arrays.asList(info));

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        ArrayAdapter<String> bevAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_layout, // The name of the layout ID.
                        R.id.list_item_textView, // The ID of the textView to populate.
                        productInfo);

        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.bev_listview);
        listView.setAdapter(bevAdapter);


        return rootView;
    }

}
