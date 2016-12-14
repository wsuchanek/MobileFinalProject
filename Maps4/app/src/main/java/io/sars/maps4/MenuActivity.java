package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.net.Inet4Address;

/**
 * Created by willsuchanek on 12/12/16.
 */
public class MenuActivity extends Activity {
    private String lat;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Intent intent = getIntent();
        lat = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");


    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String bgColor = SP.getString("bgColor","1");
        if (bgColor.equals("1")){
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(MenuActivity.this, R.color.cabernet));
        } else if (bgColor.equals("2")){
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(MenuActivity.this, R.color.green));
        }else{
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(MenuActivity.this, R.color.white));
        }
    }

    public void toSurvey(View view){
        Intent surveyIntent = new Intent(this, SurveyActivity.class);
        surveyIntent.putExtra("latitude", lat);
        surveyIntent.putExtra("longitude", longitude);
        startActivity(surveyIntent);
    }
    public void toAbout(View view){
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }
    public void toListView(View view){
        Intent listIntent = new Intent(this, ListActivity.class);
        startActivity(listIntent);
    }
    public void goBacktoMap(View view){finish();}
}
