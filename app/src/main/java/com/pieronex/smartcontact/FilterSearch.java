package com.pieronex.smartcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        setupwidgetlistener();
    }

    public void bindwidget(){
        RadioName = (RadioButton)findViewById(R.id.displayname);
        RadioNickname = (RadioButton)findViewById(R.id.nickname);
        Radiophone = (RadioButton)findViewById(R.id.phonenumber);
        Radiomail = (RadioButton)findViewById(R.id.email_filter);
        Radiotag = (RadioButton)findViewById(R.id.tag);
     }

    public void setupwidgetlistener(){
        RadioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RadioName.isChecked()) {
                    RadioName.setChecked(false);
                }
                else {
                    RadioName.setChecked(true);
                }
            }
        });

        RadioNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RadioNickname.isChecked()) {
                    RadioNickname.setChecked(false);
                }
                else {
                    RadioNickname.setChecked(true);
                }
            }
        });

        Radiophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Radiophone.isChecked()) {
                    Radiophone.setChecked(false);
                }
                else {
                    Radiophone.setChecked(true);
                }
            }
        });

        Radiomail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Radiomail.isChecked()) {
                    Radiomail.setChecked(true);
                }
                else {
                    Radiomail.setChecked(false);
                }
            }
        });

        Radiotag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Radiotag.isChecked()) {
                    Radiotag.setChecked(false);
                }
                else {
                    Radiotag.setChecked(true);
                }
            }
        });
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
