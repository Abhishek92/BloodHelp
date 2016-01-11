package com.android.bloodhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.bloodhelp.backend.PersonProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        double latitude = Double.parseDouble(BloodHelpApp.getSavePrefsInstance(this).getCurrentLatitude());
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
        });
    }
}
