package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.ContentResolver;

import android.database.Cursor;
import android.graphics.Picture;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
//---------

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;

/**
 * Created by win.thitiwat on 11/20/2014.
 */
public class Account extends Activity implements Parcelable{//extends Observable{
    private int stat_of_search = 0;
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private String nickName = "";
    private String displayName = "";
    private String phoneNo = "";
    private String email = "";
    private String pictureProfile;
    private String address = "";
    private String organization = "";
    private String id = "";
    private ArrayList<String> tags;
    private ContentResolver contentResolver;

        // display, phoneNo, address, organization
    public Account(String mDisplayName, String mPhoneNo, String mEmail, String mAddress, String mOrganization, String mId, ContentResolver mcontentResolver){
        displayName = mDisplayName;
        phoneNo = mPhoneNo;
        address = mAddress;
        organization = mOrganization;
        id = mId;
        email = mEmail;
        contentResolver = mcontentResolver;
    }

//    public Account(String _firstName, String _lastName , String _middleName ,String _nickName, String _phoneNo, String _email, String _pictureProfile){
//        firstName = _firstName;
//        lastName = _lastName;
//        middleName = _middleName;
//        nickName = _nickName;
//        phoneNo = _phoneNo;
//        email = _email;
//        pictureProfile = _pictureProfile;
//    }

    public Account(String mFirstName, String mPhoneNo, String mId, ContentResolver mcontentResolver){

        this(mFirstName, mPhoneNo, "0","1", "2", mId, mcontentResolver);
    }



    public String getDisplayName() {
       // ContentResolver cr = contentResolver;
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext())
//                displayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//        }
        return displayName;
    }





    public String getPhoneNo() {
        return phoneNo;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public List<String> getEmail(ContentResolver contentResolver) {
        ArrayList<String> result = new ArrayList<String>();
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);


        while (emailCur.moveToNext()) {
            // This would allow you get several email addresses
            // if the email addresses were stored in an array
            String mEmail = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            String emailType = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            result.add(mEmail);
            //account.setEmail(mEmail);
            // System.out.println("Email " + mEmail + " Email Type : " + emailType);
//                    }
        }
        emailCur.close();
        return result;
    }

    public String getPictureProfile() {
        return pictureProfile;
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

    public void setPictureProfile(String pictureProfile) {
        this.pictureProfile = pictureProfile;
    }

    public int getStat_of_search() {
        return stat_of_search;
    }

    public void setStat_of_search(int stat_of_search) {
        this.stat_of_search = stat_of_search;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
//String mDisplayName, String mPhoneNo, String mEmail, String mAddress, String mOrganization, String mId)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeStringArray(new String[]{displayName, phoneNo, email, address, organization, id});// , email, pictureProfile});
        //dest.writeArray(new Object[]{contentResolver});
    }

    public Account(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        displayName = data[0];
        phoneNo = data[1];
        email = data[2];
        address = data[3];
        organization =data[4];
        id = data[5];

        //Objects[] data2 = new Objects[1];
        //in.readArray(data2);
      //  pictureProfile = data[6];

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String toString() {
        return displayName + " "  + "\n\t"+ "- No: "+ phoneNo + "\n";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


}
