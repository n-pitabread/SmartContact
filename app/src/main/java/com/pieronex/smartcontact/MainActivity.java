package com.pieronex.smartcontact;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import  com.pieronex.smartcontact.R;

import java.util.Observer;


public class MainActivity extends Activity implements Observer, View.OnClickListener {
    private Account accountModel;
    private SearchView searchBar;
    ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView NoContact;

    String[] values = new String[] { "Android List View",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
//        accountModel = new Account();
//        NoContact = (TextView) findViewById(R.id.NoContact);
//        listView= (ListView) findViewById(R.id.android_list);
//        if(values.length != 0)
//            NoContact.setVisibility(View.INVISIBLE);
//
//        accountModel.addObserver(this); // Add this activity to be the observer of the model
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);
//        //searchBar
//
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);
//
//        // ListView Item Click Listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) listView.getItemAtPosition(position);
//
//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
//
//            }
//
//        });
//

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
    public String getAccountFirstName() {
        return accountModel.getFirstName();
    }

    public void setAccountFirstName(String accountFirstName) {
        accountModel.setFirstName(accountFirstName);
    }

    public String getAccountLastName() {
        return accountModel.getLastName();
    }

    public void setAccountLastName(String accountLastName) {
        accountModel.setLastName(accountLastName);
    }

    public String getAccountDisplayName() {
        return accountModel.getDisplayName();
    }

    public void setAccountDisplayName(String accountDisplayName) {
        accountModel.setDisplayName(accountDisplayName);
    }

    public String getAccountPhoneNo() {
        return accountModel.getPhoneNo();
    }

    public void setAccountPhoneNo(String accountPhoneNo) {
        accountModel.setPhoneNo(accountPhoneNo);
    }

    public String getAccountEMail() {
        return accountModel.getEmail();
    }

    public void setAccountEMail(String accountEMail) {
        accountModel.setEmail(accountEMail);
    }

    public Picture getAccountPictureProfile() {
        return accountModel.getPictureProfile();
    }

    public void setAccountPictureProfile(Picture accountPictureProfile) {
        accountModel.setPictureProfile(accountPictureProfile);
    }
}
