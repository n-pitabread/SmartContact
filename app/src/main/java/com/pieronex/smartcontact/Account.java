package com.pieronex.smartcontact;

import android.graphics.Picture;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;

/**
 * Created by win.thitiwat on 11/20/2014.
 */
public class Account implements Parcelable{//extends Observable{
    private int stat_of_search = 0;
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private String nickName = "";
    private String displayName = "";
    private String phoneNo = "";
    private String email = "";
    private String pictureProfile;
    private ArrayList<String> tags;



    public Account(String _firstName, String _lastName , String _middleName ,String _nickName, String _phoneNo, String _email, String _pictureProfile){
        firstName = _firstName;
        lastName = _lastName;
        middleName = _middleName;
        nickName = _nickName;
        phoneNo = _phoneNo;
        email = _email;
        pictureProfile = _pictureProfile;
    }

    public Account(String mFirstName, String mPhoneNo){

        this(mFirstName, mPhoneNo, "1", "2", "3", "4", "5");
    }



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

    public String getPictureProfile() {
        return pictureProfile;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

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

    public void setPictureProfile(String pictureProfile) {
        this.pictureProfile = pictureProfile;
    }

    public int getStat_of_search() {
        return stat_of_search;
    }

    public void setStat_of_search(int stat_of_search) {
        this.stat_of_search = stat_of_search;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeStringArray(new String[]{firstName, lastName, middleName, nickName, phoneNo, email, pictureProfile});
    }

    public Account(Parcel in){
        String[] data = new String[7];
        in.readStringArray(data);
        firstName = data[0];
        lastName = data[1];
        middleName = data[2];
        nickName = data[3];
        phoneNo =data[4];
        email = data[5];
        pictureProfile = data[6];

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
