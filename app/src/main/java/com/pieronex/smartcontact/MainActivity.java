package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.Observable;
import java.util.Observer;

import  com.pieronex.smartcontact.R;

import java.util.Observer;


public class MainActivity extends Activity implements Observer, View.OnClickListener {
    private Account accountModel;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountModel = new Account();
        accountModel.addObserver(this); // Add this activity to be the observer of the model

        //searchBar
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
