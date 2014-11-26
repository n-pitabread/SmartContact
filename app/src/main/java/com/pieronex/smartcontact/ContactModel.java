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
 * <h1>Contact Model</h1>
 * This class consists of attributes of list of {@link Account}.
 *
 *
 * @author Puriwat Khantiviriya
 * @author Sasawat Chanate
 * @author Thitiwat Watanajaturaporn
 * @version 1.0
 * @since   2014-11-25
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


    /**
     * Load data from phone's database
     * It will be retrieving id, phone number, given name, nickname, email, and tag
     *
     *
     * @param mContentResolver provides application access to the content model
     */
    public static void loadAccounts(ContentResolver mContentResolver) {
        /*Declaration variable */

        /*creates account for being sent back to the function that call this method */
        accounts = new ArrayList<Account>();
        /*to provide applications access to the content model. */
        /*providers manage access to a structured set of data. */
        /*They encapsulate the data, and provide mechanisms for defining data security.*/
        ContentResolver contentResolver = mContentResolver;

        /*to provide random read-write access to the result set returned by a database query. */
        Cursor cursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, QUERY_SORTED_DISPLAYNAME);
        /*check if there are a great number of contacts*/

        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
            /* for keeping values from DataBase, */
            /* and then these 3 variables will be used - */
            /*as parameter for creating an object Account*/
            String mId = "", mPhoneNo = "", mGivenName = "", mNickname = "", mEmail = "", mTag = "";
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
            }
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
                            ContactsContract.CommonDataKinds.Nickname._ID + "=?",
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
                Cursor contactsCursor = null;
                try {
                    contactsCursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                            new String [] { ContactsContract.RawContacts._ID },
                            ContactsContract.CommonDataKinds.Note._ID + "=?", new String[] { mId }, null);
                    if (contactsCursor != null && contactsCursor.moveToFirst()) {
                        do {
                            String rawContactId = contactsCursor.getString(0);
                            Cursor noteCursor = null;
                            try {
                                noteCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                                        new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Note.NOTE},
                                        ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND "
                                                + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE + "'",
                                        new String[]{rawContactId}, null);

                                if (noteCursor != null && noteCursor.moveToFirst()) {
                                    mTag = noteCursor.getString(noteCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                                }
                            } finally {
                                if (noteCursor != null) {
                                    noteCursor.close();
                                }
                            }

                        } while (contactsCursor.moveToNext());
                    }
                } finally {
                    if (contactsCursor != null) {
                        contactsCursor.close();
                    }
                }
                ///*******************************************************************///
                //*create Account with 3 params, and put into the accounts list*/
                accounts.add(new Account(mGivenName, mPhoneNo, mEmail,mNickname, mTag, mId, contentResolver));
        }
    }



    /**
     * Apply filter for searching only specific categories
     *
     *
     * @param accountList list of all accounts
     * @param search input search keyword
     */
    public static void applyFilter(ArrayAdapter<Account> accountList, String search) {

        accountList.clear();

//        for(int i = 0; i < checkList.length; i++){
//            if(checkList[i] == true){  // if Toggle is YES
//
//            }
//        }


        for(Account account: accounts)
        {
            if(account.getDisplayName().toLowerCase().contains(search.toLowerCase()) ||
                account.getPhoneNo().replace(" ", "").contains(search) ||
                account.getTags().toLowerCase().contains(search.toLowerCase()) ||
                account.getNickName().toLowerCase().contains(search.toLowerCase()))
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
