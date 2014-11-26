package com.pieronex.smartcontact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win.thitiwat on 11/25/2014.
 */
public class ContactModel {
    static List<Account> accounts = null;
    private static final String QUERY_SORTED_DISPLAYNAME = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

    public static List<Account> getAccounts(ContentResolver contentResolver) {
        if(accounts == null) {
            loadAccounts(contentResolver);
        }
        return accounts;
    }


    /*This method is for retrieving data from phone's DataBase */
    /* This method will retrieve displayName, phoneNo, and id's person*/
    public static void loadAccounts(ContentResolver mContentResolver) {
        /*Declaration variable */

        /*creates account for being sent back to the function that call this method */
        accounts = new ArrayList<Account>();
        /* for keeping values from DataBase, */
        /* and then these 3 variables will be used - */
        /*as parameter for creating an object Account*/
        String mId = "", mPhoneNo = "", mGivenName = "", mNickname = "", mEmail = "", mTag = "";
        /*to provide applications access to the content model. */
        /*providers manage access to a structured set of data. */
        /*They encapsulate the data, and provide mechanisms for defining data security.*/
        ContentResolver contentResolver = mContentResolver;

        /*to provide random read-write access to the result set returned by a database query. */
        Cursor cursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, QUERY_SORTED_DISPLAYNAME);
        /*check if there are a great number of contacts*/

        if (cursor.getCount() > 0) while (cursor.moveToNext()) {
                /*get id's contact*/
            mId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                /*get display name contact*/
            mGivenName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

//            mNickname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));

            ///*******************************************************************///
            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    /*move cursor to phone's column*/
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{mId}, null);
                 /*looping get phoneNo*/
                while (phoneCursor.moveToNext()) {
                        /*get phone no*/
                    mPhoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phoneCursor.close();
                ///*******************************************************************///
                /*Retrieve Nickname info*/
//                String[] proj = {ContactsContract.CommonDataKinds.Nickname.NAME, ContactsContract.CommonDataKinds.Nickname.TYPE};
//                Cursor nicknameCursor = mContentResolver.query(
//                        ContactsContract.Data.CONTENT_URI, proj, ContactsContract.CommonDataKinds.Nickname.CONTACT_ID + " = ?", new String[]{mId}, null);
////                ContactsContract.Data.CONTENT_URI, proj,ContactsContract.CommonDataKinds.Nickname.CONTACT_ID +" = "+ recordId, null, null); // old one
//                while (nicknameCursor.moveToNext()) {
//                    mNickname = nicknameCursor.getString(
//                            nicknameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
//                }
//                nicknameCursor.close();
                ////*******************************************************************///
                try {
                    //Cursor nickNamecursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + " = ?", new String[]{String.valueOf(mId)}, null);
                    Cursor nickNamecursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                            new String[]{ContactsContract.Data.DISPLAY_NAME},
                            ContactsContract.CommonDataKinds.Nickname.DATA1 + "=?",
                            new String[] { mId },
                            null);
                    while (nickNamecursor.moveToNext()) {
//                        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
//                        String[] params = new String[]{String.valueOf(mId), ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE};
//                        Cursor nickname = mContentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, params, null);
//                        while (nickname.moveToNext()) {
                            mNickname = nickNamecursor.getString(nickNamecursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                            String nicknameType = nickNamecursor.getString(nickNamecursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.TYPE));

//                        }
                    }
                    nickNamecursor.close();
                }catch(Exception e){
                    Log.d("Error occur", "WARNING!!!");
                }
                //**************************************************************************/////
//                Cursor nickNamecursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
//                        new String[]{ContactsContract.Data.DISPLAY_NAME},
//                        ContactsContract.CommonDataKinds.Nickname.DATA1 + "=?",
//                        new String[] { mId },
//                        null);
//
//                if (cursor.moveToFirst())
//                    String nameOfContact = nickNamecursor.getString(0);


                /*Retrieve Email*/
                Cursor emailCur = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{mId}, null);

                while (emailCur.moveToNext()) {
                    mEmail = emailCur.getString(
                            emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                emailCur.close();
                ///*******************************************************************///
                /*retrieve tag or note from the divu*/


                ///*******************************************************************///
                //*create Account with 3 params, and put into the accounts list*/
                accounts.add(new Account(mGivenName, mPhoneNo, mEmail,mNickname, mTag, mId, contentResolver));

            }
        }
    }



    /*this method is filter word from searchBar, */
    /*and then show contacts that relate to word in search*/
    public static void applyFilter(ArrayAdapter<Account> accountList, String search) {

        accountList.clear();

        for(Account account: accounts)
        {
            if(account.getDisplayName().toLowerCase().contains(search.toLowerCase()) || account.getPhoneNo().contains(search))
            {
                accountList.add(account);
            }
        }
    }


}



/*

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
*/
