package com.pieronex.smartcontact;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class FilterSearch extends Activity {

    private RadioButton RadioName;
    private RadioButton RadioNickname;
    private RadioButton Radiophone;
    private RadioButton Radiotag;
    private RadioButton Radiomail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        bindwidget();
    }

    public void bindwidget(){
        RadioName = (RadioButton)findViewById(R.id.displayname);
        RadioNickname = (RadioButton)findViewById(R.id.nickname);
        Radiophone = (RadioButton)findViewById(R.id.phonenumber);
        Radiomail = (RadioButton)findViewById(R.id.email_filter);
        Radiotag = (RadioButton)findViewById(R.id.tag);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
