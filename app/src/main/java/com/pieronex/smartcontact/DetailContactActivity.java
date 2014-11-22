package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DetailContactActivity extends Activity {

    private String infoContact;
    TextView nameContact;
//    public DetailContactActivity(String info) {
//        setInfoContact(info);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        bindWidget();
        getActionBar().hide();
        nameContact.setText(infoContact);

        Intent inboundIndex = getIntent();
        String _firstName = inboundIndex.getStringExtra("name");
        Toast.makeText(getApplicationContext(), _firstName, Toast.LENGTH_SHORT).show();
        nameContact.setText(_firstName);

    }




    public void bindWidget(){
        nameContact = (TextView)findViewById(R.id.name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String getInfoContact() {
        return infoContact;
    }

    public void setInfoContact(String infoContact) {
        this.infoContact = infoContact;
    }
}
