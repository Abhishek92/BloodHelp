package com.android.bloodhelp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by hp pc on 02-01-2016.
 */
public class BloodHelpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getResources().getString(R.string.parse_app_id)
                , getResources().getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(this);

    }
}
