package com.android.bloodhelp.backend;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

/**
 * Created by hp pc on 09-01-2016.
 */
@ParseClassName("PersonProfile")
public class PersonProfile extends ParseUser {

    private String facebookId;
    private String gender;
    private String dateOfBirth;
    private String bloodGroup;
    private String address;
    private String city;
    private String userState;
    private String profilePic;
    private ParseGeoPoint userLocation;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public ParseGeoPoint getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(ParseGeoPoint userLocation) {
        this.userLocation = userLocation;
    }
}
