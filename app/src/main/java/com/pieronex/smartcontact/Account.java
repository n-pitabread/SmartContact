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
public class Account extends  Activity implements Parcelable {//extends Observable{
    private String displayName ;//
    private String phoneNo ;//
    private String email ;//
    private String address ;//
    private String organization ;//
    private String id ;//
    private ArrayList<String> tags;
    private ContentResolver contentResolver;//


    public Account(String mDisplayName, String mPhoneNo, String mEmail, String mAddress, String mOrganization, String mId, ContentResolver mContentResolver){
        displayName = mDisplayName;
        phoneNo = mPhoneNo;
        address = mAddress;
        organization = mOrganization;
        id = mId;
        email = mEmail;
        contentResolver = mContentResolver;
    }


    public Account(String mFirstName, String mPhoneNo, String mId, ContentResolver mcontentResolver){

        this(mFirstName, mPhoneNo, null, null, null , mId, mcontentResolver);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public ArrayList<String> getTags() {
        return tags;
    }


    public List<String> getEmail(ContentResolver mContenResolver){
        ArrayList<String> emails = new ArrayList<String>();
        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{this.getId()}, null);

        while (emailCur.moveToNext()) {
            // This would allow you get several email addresses
            // if the email addresses were stored in an array
            String mEmail = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            emails.add(mEmail);

        }
        emailCur.close();
        return emails;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String toString(){
        return displayName +"\n\t"+ "Tel. " + phoneNo + "\n";
    }

    /*Constructor for receive Object in Parcel data type*/
    public Account(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        /*set "displayName" to data[0];*/
        setDisplayName(data[0]); //displayName = data[0];
        /*set "phoneNo" to data[1];*/
        setPhoneNo(data[1]);
        /*email = data[2];*/
        setEmail(data[2]);
        /*address = data[3];*/
        setAddress(data[3]);
        /*organization =data[4];*/
        setOrganization (data[4]);
        /*id = data[5];*/
        setId (data[5]);
    }


    ////////////////////////////////////////////////////////////
    ///* This below part is for making Account as Parcelable  */
    ///* So that Intent can send an Object through parameter  */
    ////////////////////////////////////////////////////////////
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{displayName, phoneNo, email, address, organization, id});
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
