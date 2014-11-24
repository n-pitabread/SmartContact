package com.pieronex.smartcontact;

import android.app.Activity;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Picture;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import  com.pieronex.smartcontact.R;

import java.util.Observer;


public class MainActivity extends Activity implements Observer, View.OnClickListener{
    //private Account accountModel;
    //ArrayList<Observer> accountModel = new ArrayList<Observer>();
    List<String> contacts = new ArrayList<String>();
    List<Account> accountModel = new ArrayList<Account>();
    private SearchView searchBar;

    //ArrayAdapter<Account> adapter;
    ArrayAdapter adapter;
    private ListView listView;
    private TextView NoContact;
    String[] values = contactList();

    ContentValues contentValues = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search);

//
        getActionBar().show();


        bindWidget();
        setWidgetEventListener();
//    readContacts();


          displayContacts();



    }

    public static void sort(List<String> contactss) {
        int j;
        for (int i = 1; i < contactss.size(); i++) {
            j = i;
            while (j > 0 && contactss.get(j).compareTo(contactss.get(j - 1)) < 0) {
                Collections.swap(contactss, j, j - 1);
                j--;
            }
        }
    }


    public void setWidgetEventListener() {
//        Collections.sort(contacts, new FirstNameComparator());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                searchBar.setQuery("",true);
//                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
//                String customer = cursor.getString(cursor.getColumnIndexOrThrow("customer"));

                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(), "Position :" + id + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
                //DetailContactActivity info = new DetailContactActivity(itemValue);

                Intent intent = new Intent(getApplicationContext(), DetailContactActivity.class);
                intent.putExtra("account", accountModel.get(position));//new Account("1","2","3","4","5","6","7"));
//                intent.putExtra("account", new Account("1","2","3","4","5","6","7"));
//                intent.putExtra("account", new Account(,"") )
                startActivity(intent);
//                searchBar.setQuery("...", true);

            }
        });

        //searchBar.setOnSearchClickListener();

    }



    public void bindWidget() {
       searchBar = (SearchView) findViewById(R.id.searchButton);
        searchBar.setActivated(false);
//        searchBar.setIconifiedByDefault(false);
//       searchBar.setOnQueryTextListener(){
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                showResults(newText + "*");
//                return false;
//            }
//        }
//        searchBar.setOnCloseListener((SearchView.OnCloseListener) this);
        NoContact = (TextView) findViewById(R.id.NoContact);
        listView = (ListView) findViewById(R.id.android_list);
        if (values.length != 0)
            NoContact.setVisibility(View.INVISIBLE);
    }

   private void displayContacts() {
       //  ContactsContract.CommonDataKinds.Photo
        String mEmail, mId, mDisplayName, mPhoneNo, mLastTimeContacted, mNickName, mMiddleName;
        String mGivenName, mFamilyName, mPrefix, mSuffix;

        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
       if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                mId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                mGivenName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{mId}, null);
                    while (pCur.moveToNext()) {
                        mPhoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contacts.add(mGivenName);
                        accountModel.add(new Account(mGivenName, mId));
                        Toast.makeText(MainActivity.this, "Name: " + mGivenName + ", ID No: " + mId, Toast.LENGTH_SHORT).show();


                    }
                    pCur.close();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // case R.id.button1:
            // accountModel. ~~~~
        }
    }




    public void readContacts() {
        String mEmail = "", mId = "", mDisplayName = "", mPhoneNo = "", mLastTimeContacted = "", mNickName = "", mMiddleName = "";
        String mGivenName = "", mFamilyName = "", mPrefix = "", mSuffix = "", mAddress = "", mNote = "", mWhereOrg = "", mInstantM = "";

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                mId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                mGivenName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
             //  ur.getString(cur.getColumnIndex(ContactsContract.Contacts.));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        //System.out.println("name : " + mGivenName + ", ID : " + mId);

                        // get the phone number
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{mId}, null);
                        while (pCur.moveToNext()) {
                            mPhoneNo = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //System.out.println("phone" + mPhoneNo);
                        }
                        pCur.close();


                        // get email and type

                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{mId}, null);
                        while (emailCur.moveToNext()) {
                            // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                            mEmail = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            String emailType = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                           // System.out.println("Email " + mEmail + " Email Type : " + emailType);
                        }
                        emailCur.close();

                        // Get note.......
                        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] noteWhereParams = new String[]{mId,
                                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                        Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                        if (noteCur.moveToFirst()) {
                            mNote = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                          //  System.out.println("Note " + mNote);
                        }
                        noteCur.close();

                        //Get Postal Address....

                        String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] addrWhereParams = new String[]{mId,
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                        Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, null, null, null);
                        while (addrCur.moveToNext()) {
                            String poBox = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                            String street = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                            String city = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                            String state = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                            String postalCode = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                            String country = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                            String type = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                            // Do something with these....
                            mAddress = poBox + ", " + street + ", " + city + ", " + state + ", " + postalCode + ", " + country;

                        }
                        addrCur.close();

                        // Get Instant Messenger.........
                        String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] imWhereParams = new String[]{mId,
                                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                        Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, imWhere, imWhereParams, null);
                        if (imCur.moveToFirst()) {
                            String imName = imCur.getString(
                                    imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                            String imType;
                            imType = imCur.getString(
                                    imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                            mInstantM = imName + ", " + imType;
                        }
                        imCur.close();

                        // Get Organizations.........

                        String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] orgWhereParams = new String[]{mId,
                                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                        Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, orgWhere, orgWhereParams, null);
                        if (orgCur.moveToFirst()) {
                            String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                            String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                            mWhereOrg = orgWhere + ", " + title;
                        }

                        orgCur.close();

                    String familyName =ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] familyNameParams = new String[]{mId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                    Cursor familyCur = cr.query(ContactsContract.Data.CONTENT_URI, null, familyName, familyNameParams, null);
                    if(familyCur.moveToNext()){
                        mFamilyName = familyCur.getString(familyCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    }
                    //cr.query(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                    contacts.add(mGivenName);
                    accountModel.add(new Account(mGivenName, mFamilyName, "middleName", "mNickName", mPhoneNo, mEmail, "Pic"));

                }

            }
        }
    }
    public String[] contactList() {
        return new String[]{
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",

        };
    }


}

    ///// ---- Account Controller Method Part --
    ///*************************************************************///
    //    getAccountLastName()
    //    setAccountLastName(String accountLastName)
    //    getAccountDisplayName()
    //    setAccountDisplayName(String accountDisplayName)
    //    getAccountPhoneNo()
    //    setAccountPhoneNo(String accountPhoneNo)
    //    getAccountEMail()
    //    setAccountEMail(String accountEMail)
    //    getAccountPictureProfile()
    //    setAccountPictureProfile(Picture accountPictureProfile)
    ///*************************************************************///

//
//    public String getAccountFirstName() {
//        return accountModel.getFirstName();
//    }
//
//    public void setAccountFirstName(String accountFirstName) {
//        accountModel.setFirstName(accountFirstName);
//    }
//
//    public String getAccountLastName() {
//        return accountModel.getLastName();
//    }
//
//    public void setAccountLastName(String accountLastName) {
//        accountModel.setLastName(accountLastName);
//    }
//
//    public String getAccountDisplayName() {
//        return accountModel.getDisplayName();
//    }
//
//    public void setAccountDisplayName(String accountDisplayName) {
//        accountModel.setDisplayName(accountDisplayName);
//    }
//
//    public String getAccountPhoneNo() {
//        return accountModel.getPhoneNo();
//    }
//
//    public void setAccountPhoneNo(String accountPhoneNo) {
//        accountModel.setPhoneNo(accountPhoneNo);
//    }
//
//    public String getAccountEMail() {
//        return accountModel.getEmail();
//    }
//
//    public void setAccountEMail(String accountEMail) {
//        accountModel.setEmail(accountEMail);
//    }
//
//    public Picture getAccountPictureProfile() {
//        return accountModel.getPictureProfile();
//    }
//
//    public void setAccountPictureProfile(Picture accountPictureProfile) {
//        accountModel.setPictureProfile(accountPictureProfile);
//    }

//private class AccountAdapter extends ArrayAdapter<Account>{
//    public AccountAdapter() {
//        super( MainActivity.this, R.layout.contact_detail, accountModel);
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent){
//        if(view == null)
//            view = getLayoutInflater().inflate(R.layout.contact_detail, parent, false);
//        Account currentAccount = accountModel.get(position);
//        TextView name = (TextView) view.findViewById(R.id.name);
//        name.setText(currentAccount.getFirstName());
//
//        return view;
//    }
//}



