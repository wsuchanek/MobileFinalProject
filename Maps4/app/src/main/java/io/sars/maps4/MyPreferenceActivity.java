package io.sars.maps4;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
/*************************************************************************************************
 * File:   MyPreferenceActivity.java
 * Author: Nicole Lane, Will Suchanek
 *
 * Created on September 16, 2012, 3:42 PM
 * Last Modified September 17, 2012, 10:41 PM
 *
 * Purpose: This Activity records the user's preferences and updates the app accordingly.
 *
 *
 *************************************************************************************************/


public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}