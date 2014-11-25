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
public class ContactModel{
    static List<Account> accounts = null;

    public static List<Account> getAccounts(ContentResolver contentResolver) {
        if(accounts == null) {
            loadAccounts(contentResolver);
        }
        return accounts;
    }

    public static void loadAccounts(ContentResolver contentResolver) {
        accounts = new ArrayList<Account>();
        String mEmail, mId, mDisplayName, mPhoneNo, mLastTimeContacted, mNickName, mMiddleName;
        String mGivenName, mFamilyName, mPrefix, mSuffix, mPicture;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        ContentResolver cr = contentResolver;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                mId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                mGivenName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mLastTimeContacted = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
//                mPicture = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
//                Log.d("image String", mPicture);
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{mId}, null);
                    while (pCur.moveToNext()) {
                        mPhoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //mEmail = pCur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
                        //Log.e("Email", mEmail);
                        //contacts.add(mGivenName + "\n\t"+ "- No: "+mPhoneNo);
                        accounts.add(new Account(mGivenName, mPhoneNo, mId, contentResolver));
//                        Uri picturePerson = getPhotoUriFromID(mId);
//                        mPicture = picturePerson.toString();
//                        Log.d("picture","picture: "+ mPicture);
                        //Toast.makeText(MainActivity.this, "Name: " + mGivenName + ", lastContact No: " + mLastTimeContacted, Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();


                }
            }
        }
    }

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
