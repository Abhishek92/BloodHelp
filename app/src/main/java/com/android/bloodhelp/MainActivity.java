package com.android.bloodhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        accessTokenTracker();
        callbackManager = CallbackManager.Factory.create();
        registerFacebookCallback();
        if(BloodHelpApp.getSavePrefsInstance(this).isUserRegisteredAlready())
        {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        else if(isLoggedIn())
            startRegistration();
    }

    private void registerFacebookCallback()
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                if (profile == null)
                    profileTracker();
                else
                    startRegistration();
            }

            @Override
            public void onCancel() {
                Log.d("MainActivity", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("MainActivity", error.getMessage());
            }
        });
    }

    private void accessTokenTracker()
    {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                AccessToken.setCurrentAccessToken(currentAccessToken);
            }
        };
        accessTokenTracker.startTracking();
    }

    private void profileTracker()
    {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Profile.setCurrentProfile(currentProfile);
                startRegistration();
            }
        };
        profileTracker.startTracking();
    }

    private void startRegistration()
    {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.logInButton)
    void logInToYourAccount() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.login_button)
    void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends","email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private boolean isLoggedIn()
    {
        return AccessToken.getCurrentAccessToken() != null;
    }
}
