package io.sars.maps4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    }
    public void goBacktoMap(View view){finish();}
}
