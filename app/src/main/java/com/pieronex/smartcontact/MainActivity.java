package com.pieronex.smartcontact;

import android.app.Activity;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.media.audiofx.AcousticEchoCanceler;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import  com.pieronex.smartcontact.R;

import java.util.Observer;


public class MainActivity extends Activity  implements Observer, View.OnClickListener {
    //private Account accountModel;
    //ArrayList<Observer> accountModel = new ArrayList<Observer>();
    List<String> contacts = new ArrayList<String>();
    List<Account> accountModel = new ArrayList<Account>();
//    private SearchView searchBar;

    //ArrayAdapter<Account> adapter;
    ArrayAdapter adapter;
    private ListView listView;
    private TextView NoContact;
    private SwipeRefreshLayout swipecontainer;
    String[] values = contactList();

    ContentValues contentValues = new ContentValues();


    //SearchBar
    //private CardView card;
    private SearchView searchBar;
    RelativeLayout relay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        super.onCreate(savedInstanceState);

//        setContentView(R.layout.search);

        getActionBar().show();
        //accountModel = new ArrayList<Account>();
        //accountModel.addObserver(this); // Add this activity to be the observer of the model

        bindWidget();

        displayContacts();

        setWidgetEventListener();
        //readContacts();


        relay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("search", "Workk!!!");
//                searchBar.setOnQueryTextListener(this);

                    //searchBar.onActionViewCollapsed();
                    searchBar.onActionViewExpanded();
                //searchBar.setSubmitButtonEnabled(true);

            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("KEY", "0");
                ContactModel.applyFilter(adapter, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("KEY", newText);
                ContactModel.applyFilter(adapter, newText);
                return false;
            }
        });

        swipecontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                swipecontainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        ContactModel.loadAccounts(getContentResolver());
                        ContactModel.applyFilter(adapter, "");
                        swipecontainer.setRefreshing(false);
                    }
                }, 0);
            }
        });

    }




    public void bindWidget() {

        NoContact = (TextView) findViewById(R.id.NoContact);
        listView = (ListView) findViewById(R.id.android_list);
        swipecontainer = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        listView.setLongClickable(true);

        //card  = (CardView) findViewById(R.id.card_view);
        relay = (RelativeLayout) findViewById(R.id.search);
        searchBar = (SearchView) findViewById(R.id.searchButton);
        searchBar.onActionViewCollapsed();
        if (values.length != 0)
            NoContact.setVisibility(View.INVISIBLE);
    }

    public void setWidgetEventListener() {



        adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, android.R.id.text1, accountModel);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), DetailContactActivity.class);
//                intent.putExtra("account", accountModel.get(position));//new Account("1","2","3","4","5","6","7"));
////                intent.putExtra("account", new Account("1","2","3","4","5","6","7"));
//
//                startActivity(intent);
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,String.valueOf(accountModel.get(position).getId()));
                i.setData(uri);
                startActivity(i);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String uri = "tel:" + accountModel.get(position).getPhoneNo();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                startActivity(callIntent);

                // -----------------------------------------
//                Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_VCARD_URI);
//                startActivity(intent);

            }
        });
    }


    private Uri getPhotoUriFromID(String id) {
        try {
            Cursor cur = getContentResolver()
                    .query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.CONTACT_ID
                                    + "="
                                    + id
                                    + " AND "
                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                    + "'", null, null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
        return Uri.withAppendedPath(person,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

    }


    private void displayContacts() {
        accountModel = new ArrayList<Account>(ContactModel.getAccounts(getContentResolver()));

    }


//    private void retrieveContactPhoto() {

//
//        Bitmap photo = null;
//
//        try {
//            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
//                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));
//
//            if (inputStream != null) {
//                photo = BitmapFactory.decodeStream(inputStream);
//                ImageView imageView = (ImageView) findViewById(R.id.img_contact);
//                imageView.setImageBitmap(photo);
//            }
//
//            assert inputStream != null;
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_add){
            Intent i = new Intent(Intent.ACTION_INSERT);
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            i.setData(uri);
            startActivity(i);
        }
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
//                    accountModel.add(new Account(mGivenName, mFamilyName, "middleName", "mNickName", mPhoneNo, mEmail, "Pic"));

                }

            }
        }
    }

}
