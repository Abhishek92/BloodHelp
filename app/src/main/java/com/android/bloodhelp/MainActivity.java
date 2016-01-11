package com.android.bloodhelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        ButterKnife.bind(this);
        accessTokenTracker();
        callbackManager = CallbackManager.Factory.create();
        registerFacebookCallback();
        if (ParseUser.getCurrentUser() != null)
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

    @OnClick(R.id.email_sign_in_button)
    void logInToYourAccount() {
        if (validate()) {
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            showProgressDialog("Logging in...");
            ParseUser.logInInBackground(email, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        cancelProgressDialog();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    } else {
                        cancelProgressDialog();
                        mEmailView.setError("Username or Password is incorrect");
                        clearViews();
                    }
                }
            });
        }
    }

    private void clearViews() {
        mEmailView.requestFocus();
        mEmailView.setText("");
        mPasswordView.setText("");
    }

    @OnClick(R.id.login_button)
    void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
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

    private boolean validate() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        if (TextUtils.isEmpty(mEmailView.getText().toString())) {
            mEmailView.setError("Username is required");
            mEmailView.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(mPasswordView.getText().toString())) {
            mPasswordView.setError("Password is required");
            mPasswordView.requestFocus();
            return false;
        } else if (mPasswordView.getText().toString().length() < 5) {
            mPasswordView.setError("Password is too short");
            mPasswordView.requestFocus();
            return false;
        } else
            return true;
    }

    protected void showProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    protected void cancelProgressDialog() {
        mProgressDialog.cancel();
    }
}
