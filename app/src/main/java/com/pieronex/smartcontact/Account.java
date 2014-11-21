package com.pieronex.smartcontact;

import android.graphics.Picture;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by win.thitiwat on 11/20/2014.
 */
public class Account extends Observable{
    private int stat_of_search = 0;
    private String firstName;
    private String lastName;
    private String displayName;
    private String phoneNo;
    private String email;
    private Picture pictureProfile;
    private ArrayList<String> tags;


    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getEmail() {
        return email;
    }

    public Picture getPictureProfile() {
        return pictureProfile;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
        setChanged();
        notifyObservers();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPictureProfile(Picture pictureProfile) {
        this.pictureProfile = pictureProfile;
    }

    public int getStat_of_search() {
        return stat_of_search;
    }

    public void setStat_of_search(int stat_of_search) {
        this.stat_of_search = stat_of_search;
    }
}
