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
public class ContactModel implements GetDatabaseInfo{
    static List<Account> accounts = null;
    private static final String QUERY_SORTED_DISPLAYNAME = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

    public static List<Account> getAccounts(ContentResolver contentResolver) {
        if(accounts == null) {
            loadAccounts(contentResolver);
        }
        return accounts;
    }

    public static void loadAccounts(ContentResolver contentResolver) {
        accounts = new ArrayList<Account>();
        String mId, mPhoneNo, mGivenName;
        ContentResolver cr = contentResolver;

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, QUERY_SORTED_DISPLAYNAME );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                mId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                mGivenName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phoneCursor = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{mId}, null);
                    while (phoneCursor.moveToNext()) {
                        mPhoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        accounts.add(new Account(mGivenName, mPhoneNo, mId, contentResolver));
                    }

                    phoneCursor.close();
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

    @Override
    public List<String> getEmailFromDB(ContentResolver contentResolver) {
        return null;

    }

    @Override
    public String getDisplayNameFromDB(ContentResolver contentResolver, Cursor cursor) {

        return cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

    }

    @Override
    public String getPhoneNoFromDB(ContentResolver contentResolver, Cursor cursor) {
        return null;
    }
}
