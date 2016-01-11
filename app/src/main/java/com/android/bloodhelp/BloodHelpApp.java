package com.android.bloodhelp;

import android.app.Application;
import android.content.Context;

import com.android.bloodhelp.backend.PersonProfile;
import com.android.bloodhelp.prefs.SavePreferences;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by hp pc on 02-01-2016.
 */
public class BloodHelpApp extends Application {

    private static SavePreferences sSavePrefs = null;
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getResources().getString(R.string.parse_app_id)
                , getResources().getString(R.string.parse_client_key));
        ParseObject.registerSubclass(PersonProfile.class);
        ParseFacebookUtils.initialize(this);

    }

    public static SavePreferences getSavePrefsInstance(Context context)
    {
        if(sSavePrefs == null)
            sSavePrefs = new SavePreferences(context);
        return sSavePrefs;
    }
}
