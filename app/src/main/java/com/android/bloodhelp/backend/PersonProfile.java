package com.android.bloodhelp.backend;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

/**
 * Created by hp pc on 09-01-2016.
 */
@ParseClassName("PersonProfile")
public class PersonProfile extends ParseUser {


    public String getFacebookId() {
        return getString("facebookId");
    }

    public void setFacebookId(String facebookId) {
        put("facebookId", facebookId);
    }

    public String getGender() {
        return getString("gender");
    }

    public void setGender(String gender) {
        put("gender", gender);
    }

    public String getDateOfBirth() {
        return getString("dob");
    }

    public void setDateOfBirth(String dateOfBirth) {
        put("dob", dateOfBirth);
    }

    public String getBloodGroup() {
        return getString("bloodGroup");
    }

    public void setBloodGroup(String bloodGroup) {
        put("bloodGroup", bloodGroup);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        put("address",address);
    }

    public String getProfilePic() {
        return getString("profile_pic");
    }

    public void setProfilePic(String profilePic) {
        put("profile_pic", profilePic);
    }

    public ParseGeoPoint getUserLocation() {
        return getParseGeoPoint("location");
    }

    public void setUserLocation(ParseGeoPoint userLocation) {
        put("location", userLocation);
    }

    public void setMobileNumber(String number)
    {
        put("mobileNo", number);
    }

    public String getMobileNumber()
    {
        return getString("mobileNo");
    }
}
