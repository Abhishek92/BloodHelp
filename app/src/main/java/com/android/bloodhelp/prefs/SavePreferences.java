package com.android.bloodhelp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp pc on 10-01-2016.
 */
public final class SavePreferences {

    Context context;
    final String user_pref = "bloodHelp";
    private SharedPreferences shared_preferences;
    static final int MODE_PRIVATE = 0;

    public SavePreferences(Context context) {
        shared_preferences = context.getSharedPreferences(user_pref, MODE_PRIVATE);
    }

    public void setRegistrationComplete(boolean flag) {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putBoolean("isRegistered", flag);
        editor.commit();

    }

    public boolean isUserRegisteredAlready() {
        return shared_preferences.getBoolean("isRegistered", false);
    }

    public void setCurrentLatitude(String latitide)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("latitude", latitide);
        editor.commit();
    }

    public void setCurrentLongitude(String longitude)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("longitude", longitude);
        editor.commit();
    }

    public String getCurrentLatitude()
    {
        return shared_preferences.getString("latitude", "");
    }

    public String getCurrentLongitude()
    {
        return shared_preferences.getString("longitude", "");
    }

    public void removeAll() {
        // Clear All Shared prefernce
        shared_preferences.edit().clear().commit();
    }
}
