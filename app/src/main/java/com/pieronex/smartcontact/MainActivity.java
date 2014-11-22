package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Picture;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import  com.pieronex.smartcontact.R;

import java.util.Observer;


public class MainActivity extends Activity implements Observer, View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().show();
        //accountModel = new ArrayList<Account>();
        //accountModel.addObserver(this); // Add this activity to be the observer of the model

        bindWidget();
        setWidgetEventListener();
        displayContacts();

    }

//    public void onListViewClicked(View view) {
//        startActivity(new Intent(getApplicationContext(), DetailContactActivity.class));
//    }

    private class AccountAdapter extends ArrayAdapter<Account>{
        public AccountAdapter() {
            super( MainActivity.this, R.layout.contact_detail, accountModel);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null)
                view = getLayoutInflater().inflate(R.layout.contact_detail, parent, false);
            Account currentAccount = accountModel.get(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(currentAccount.getFirstName());

            return view;
        }
    }

    public void setWidgetEventListener(){

         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(), "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
                //DetailContactActivity info = new DetailContactActivity(itemValue);

                Intent intent = new Intent(getApplicationContext(), DetailContactActivity.class);
                intent.putExtra("name", itemValue);
                startActivity(intent);
            }
        });
    }

    public void bindWidget(){
        NoContact = (TextView) findViewById(R.id.NoContact);
        listView= (ListView) findViewById(R.id.android_list);
        if(values.length != 0)
            NoContact.setVisibility(View.INVISIBLE);
    }

    private void displayContacts() {

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(name);
                        Toast.makeText(MainActivity.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();

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
        switch (v.getId()){
            // case R.id.button1:
            // accountModel. ~~~~
        }
    }

    public String[] contactList(){
        return new String[] {"",
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
}
