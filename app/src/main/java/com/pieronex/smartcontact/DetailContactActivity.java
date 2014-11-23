package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DetailContactActivity extends Activity {

    private String nameinfo, nicknameinfo, telinfo, tagsinfo;
    TextView nameContact;
    TextView nickname;
    TextView phoneNo_mobile;
    TextView tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        bindWidget();
        getActionBar().hide();


        //String _firstName = inboundIndex.getStringExtra("name");
        //Toast.makeText(getApplicationContext(), _firstName, Toast.LENGTH_SHORT).show();
        setUI();

    }




    public void bindWidget(){
        nickname = (TextView)findViewById(R.id.nickname);
        phoneNo_mobile = (TextView)findViewById(R.id.phoneNo);
        tags = (TextView)findViewById(R.id.tags);
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

    public void setInfoContact(String nameinfo, String nicknameinfo, String telinfo, String tagsinfo) {
        Intent inboundIndex = getIntent();
        this.nameinfo = inboundIndex.getStringExtra();
        this.nicknameinfo = nicknameinfo;
        this.telinfo = telinfo;
        this.tagsinfo =
    }

    public void setUI(){
        nameContact.setText(nameinfo);
        nickname.setText(nicknameinfo);
        phoneNo_mobile.setText(telinfo);
        tags.setText(tagsinfo);
    }
}
