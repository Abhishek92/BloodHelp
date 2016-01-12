package com.android.bloodhelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*double latitude = Double.parseDouble(BloodHelpApp.getSavePrefsInstance(this).getCurrentLatitude());
        double longitude = Double.parseDouble(BloodHelpApp.getSavePrefsInstance(this).getCurrentLongitude());
        ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
        ParseQuery<ParseUser> query = PersonProfile.getQuery();
        query.whereWithinKilometers("location", geoPoint, 10.0);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null)
                {
                    System.out.println(objects);
                }
                else
                    Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
