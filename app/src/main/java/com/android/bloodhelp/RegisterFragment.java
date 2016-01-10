package com.android.bloodhelp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.bloodhelp.backend.PersonProfile;
import com.android.bloodhelp.utils.DialogUtils;
import com.android.bloodhelp.utils.Utils;
import com.facebook.Profile;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment {

    private final int PLACE_PICKER_REQUEST = 1;
    @Bind(R.id.frag_profile_image)
    ImageView mProfileImg;
    @Bind(R.id.frag_username)
    EditText mUserName;
    @Bind(R.id.frag_pick_dob)
    EditText mDateofBirth;
    @Bind(R.id.frag_pick_place)
    EditText mPickPlace;
    @Bind(R.id.frag_blood_group_type)
    Spinner mBloodGroupType;
    @Bind(R.id.frag_email)
    EditText mEmailId;
    @Bind(R.id.frag_gender_rg)
    RadioGroup mGenderRadioGroup;
    @Bind(R.id.frag_male_rb)
    RadioButton mMaleRb;
    @Bind(R.id.frag_female_rb)
    RadioButton mFemaleRb;
    @Bind(R.id.frag_others_rb)
    RadioButton mOthersRb;

    private Profile mProfile;
    private ParseGeoPoint parseGeoPoint;
    private Uri profilePic;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onFragmentReady(View view) {
        setFacebookProfileData();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_register;
    }

    private void setFacebookProfileData()
    {
        mProfile = Profile.getCurrentProfile();
        if(mProfile != null)
        {
            mUserName.setText(mProfile.getName());
            setUpProfileImage();
        }
    }

    private void setUpProfileImage()
    {
        profilePic = mProfile.getProfilePictureUri(Utils.dpToPx(getActivity(), 120), Utils.dpToPx(getActivity(), 120));
        Picasso.with(getActivity())
                .load(profilePic)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(mProfileImg);
    }

    @OnClick(R.id.frag_pick_dob)
    public void pickDateOfBirth()
    {
        DialogUtils.datePickerDialog(getActivity(), mDateofBirth);
    }

    @OnClick(R.id.frag_pick_place)
    public void openPlacePickerWidget()
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.frag_submit_btn)
    public void registerUser()
    {
        if(validate()){
            ParseUser profile = new ParseUser();
            profile.setUsername(mUserName.getText().toString());
            profile.setEmail(mEmailId.getText().toString());
            profile.setPassword(mProfile.getId());
            profile.put("address", mPickPlace.getText().toString());
            profile.put("facebookId", mProfile.getId());
            profile.put("gender", getUserGender());
            profile.put("profile_pic", profilePic.toString());
            profile.put("dob", mDateofBirth.getText().toString());
            profile.put("bloodGroup", mBloodGroupType.getSelectedItem().toString());
            profile.put("location", parseGeoPoint);

            showProgressDialog("Loading...");
            profile.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        cancelProgressDialog();
                        Toast.makeText(getActivity(), "User registered", Toast.LENGTH_SHORT).show();
                        BloodHelpApp.getSavePrefsInstance(getActivity()).setRegistrationComplete(true);
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finish();
                    } else {
                        cancelProgressDialog();
                        Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getUserGender()
    {
        switch (mGenderRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.frag_male_rb:
                return mMaleRb.getText().toString();
            case R.id.frag_female_rb:
                return mFemaleRb.getText().toString();
            case R.id.frag_others_rb:
                return mOthersRb.getText().toString();
            default:
                return "";
        }
    }

    private ParseGeoPoint getUsersGeoPoints(double latitude, double longitude)
    {
        parseGeoPoint = new ParseGeoPoint();
        parseGeoPoint.setLatitude(latitude);
        parseGeoPoint.setLongitude(longitude);

        return parseGeoPoint;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                getUsersGeoPoints(place.getLatLng().latitude, place.getLatLng().longitude);
                mPickPlace.setText(place.getAddress());
                mPickPlace.setLines(4);
            }
        }
    }

    private boolean validate(){
        if(TextUtils.isEmpty(mUserName.getText().toString())){
            mUserName.setError("Username is required");
            mUserName.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mEmailId.getText().toString())){
            mEmailId.setError("Email is required");
            mEmailId.requestFocus();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mEmailId.getText().toString()).matches()){
            mEmailId.setError("Email is not correct");
            mEmailId.requestFocus();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mEmailId.getText().toString()).matches()){
            mEmailId.setError("Email is not correct");
            mEmailId.requestFocus();
            return false;
        }
        else if(mBloodGroupType.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(),"Blood group is required", Toast.LENGTH_SHORT).show();
            mBloodGroupType.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mDateofBirth.getText().toString())){
            Toast.makeText(getActivity(), "Date of birth is required", Toast.LENGTH_SHORT).show();
            mDateofBirth.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mPickPlace.getText().toString())){
            Toast.makeText(getActivity(), "Location is required", Toast.LENGTH_SHORT).show();
            mPickPlace.requestFocus();
            return false;
        }
        else
            return true;
    }
}
