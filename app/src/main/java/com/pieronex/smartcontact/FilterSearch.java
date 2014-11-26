package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;


public class FilterSearch extends Activity {

    private ToggleButton ToggleName;
    private ToggleButton ToggleNickname;
    private ToggleButton Togglephone;
    private ToggleButton Togglemail;
    private ToggleButton Toggletag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        bindwidget();
        setupwidgetlistener();
    }

    public void bindwidget(){
        ToggleName = (ToggleButton)findViewById(R.id.displayname);
        ToggleNickname = (ToggleButton)findViewById(R.id.nickname);
        Togglephone = (ToggleButton)findViewById(R.id.phonenumber);
        Togglemail = (ToggleButton)findViewById(R.id.email_filter);
        Toggletag = (ToggleButton)findViewById(R.id.tag);
     }

    public void setupwidgetlistener(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            //DO SOMETHINGS!!!!!!!!!!!
            Intent data = new Intent();
        data.putExtra("choice", "something");
            Log.d("Finish", "go back to Parents");

            setResult(RESULT_OK, data);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
