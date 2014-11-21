package com.pieronex.smartcontact;

/**
 * Created by win.thitiwat on 11/20/2014.
 */

import android.graphics.Picture;

import android.content.ContentValues;

public class DBHandler {
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_DISPLAYNAME = "displayName";
    public static final String KEY_PHONE_NO = "phoneNo";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PICTURE_PROFILE = "pictureProfile";
    public static final String DATABASE_BASE_NAME = "mydatabase";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "create table mytable (" +
            "firstName text not null, lastName text not null, displayName text not null," +
            "phoneNo text not null, email text not null)";


//    Context context;


}
